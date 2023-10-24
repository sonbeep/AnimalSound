package com.example.animalsound.view.act;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;


import com.example.animalsound.OnMainCallback;
import com.example.animalsound.R;
import com.example.animalsound.view.fragment.BaseFragment;
import com.example.animalsound.view.fragment.CommonFragment;
import com.example.animalsound.view.fragment.M001MainFragment;

import java.lang.reflect.Constructor;

public abstract class BaseAct<T extends ViewBinding, V extends ViewModel> extends AppCompatActivity implements View.OnClickListener, OnMainCallback {
    protected T binding;
    protected V viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = initViewBinding();
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(getClassVM());
        initView();
    }

    protected abstract Class<V> getClassVM();

    protected abstract void initView();

    protected abstract T initViewBinding();

    @Override
    public  void onClick(View view){}

    @Override
    public void showFragment(String tag, Object data, boolean isBack){
        try {
            Class<?> calzz = Class.forName(tag);
            Constructor<?> constructor = calzz.getConstructor();
            BaseFragment<?,?> baseFragment = (BaseFragment<?,?>) constructor.newInstance();
            baseFragment.setCallback(this);
            baseFragment.setData(data);
            FragmentTransaction tran = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(com.example.animalsound.R.id.ln_main, baseFragment, M001MainFragment.TAG);
            if (isBack){
                tran.addToBackStack(null);
            }
            tran.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
