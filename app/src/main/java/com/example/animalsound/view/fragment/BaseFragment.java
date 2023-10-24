package com.example.animalsound.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.example.animalsound.OnMainCallback;

public abstract class BaseFragment<T extends ViewBinding, V extends ViewModel> extends Fragment implements View.OnClickListener {
    protected Object data;
    protected Context context;
    protected T binding;
    protected V viewModel;
    protected OnMainCallback callback;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setCallback(OnMainCallback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = initViewBinding(inflater, container);
        viewModel = new ViewModelProvider(this).get(getClassVM());
        return binding.getRoot();
    }

    protected abstract Class<V> getClassVM();

    @Override
    public final void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        
    }

    protected abstract void initView();

    protected abstract T initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    @Override
    public final void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        clickView(v);
    }

    protected void clickView(View v) {
    }
}
