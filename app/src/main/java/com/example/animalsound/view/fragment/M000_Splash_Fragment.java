package com.example.animalsound.view.fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.animalsound.R;
import com.example.animalsound.databinding.M000SplashFragmentBinding;
import com.example.animalsound.viewmodel.CommonVM;

public class M000_Splash_Fragment extends BaseFragment<M000SplashFragmentBinding, CommonVM> {
    public static final String TAG = M000_Splash_Fragment.class.getName();

    @Override
    protected Class<CommonVM> getClassVM() {
        return CommonVM.class;
    }

    @Override
    protected void initView() {
//        binding.ivEarthUp.setAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_bg_down));
//        binding.ivEarthDown.setAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_bg_up));
        callback.disableDrawer();
        new Handler().postDelayed(() -> gotoMainScreen(), 2000);
    }

    private void gotoMainScreen() {
        callback.showFragment(M001MainFragment.TAG, null, false);
    }

    @Override
    protected M000SplashFragmentBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return M000SplashFragmentBinding.inflate(inflater, container, false);
    }


}
