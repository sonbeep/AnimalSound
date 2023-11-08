package com.example.animalsound.view.act;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.animalsound.R;
import com.example.animalsound.databinding.ActivityMainBinding;
import com.example.animalsound.view.fragment.M000_Splash_Fragment;
import com.example.animalsound.view.fragment.M001MainFragment;
import com.example.animalsound.viewmodel.CommonVM;

public class MainActivity extends BaseAct<ActivityMainBinding, CommonVM> {


    public static final String TYPE_MAMAL = "mamal";
    public static final String TYPE_SEA = "sea";
    public static final String TYPE_BIRD = "bird";


    @Override
    protected Class<CommonVM> getClassVM() {
        return CommonVM.class;
    }

    @Override
    protected void initView() {

        showFragment(M000_Splash_Fragment.TAG, null, false);

//        showFragment(M001MainFragment.TAG, null, false);
        binding.menu.trMamal.setOnClickListener(this);
        binding.menu.trBird.setOnClickListener(this);
        binding.menu.trSea.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in));
        if (view.getId() == R.id.tr_mamal){
            showListAnimal(TYPE_MAMAL);
        }else  if (view.getId() == R.id.tr_bird){
            showListAnimal(TYPE_BIRD);
        }else  if (view.getId() == R.id.tr_sea){
            showListAnimal(TYPE_SEA);
        }
    }

    private void showListAnimal(String typeAnimal) {
        Fragment frg = getSupportFragmentManager().findFragmentByTag(M001MainFragment.TAG);
        if (frg == null) return;
        ((M001MainFragment) frg).showListAnimal(typeAnimal);
        binding.drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected ActivityMainBinding initViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void makeBackArrow(String typeAnimal) {
        disableDrawer();
        binding.actionbar.getRoot().setVisibility(View.VISIBLE);
        binding.actionbar.ivMenu.setImageResource(R.drawable.ic_back);
        binding.actionbar.tvTitle.setText(typeAnimal);

        binding.actionbar.ivMenu.setOnClickListener(v->{
            onBackPressed();
        });
    }

    @Override
    public void disableDrawer() {
        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void enableDrawer() {

        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        binding.actionbar.getRoot().setVisibility(View.VISIBLE);
        binding.actionbar.ivMenu.setImageResource(R.drawable.ic_menu);
        binding.actionbar.tvTitle.setText(R.string.app_name);

        binding.actionbar.ivMenu.setOnClickListener(v->{
            binding.drawer.openDrawer(GravityCompat.START);
        });
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0){
            askForExitApp();
            return;
        }
        super.onBackPressed();
    }

    private void askForExitApp() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Aleart");
        alertDialog.setMessage("Close this app");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Close", (dialogInterface, i) -> super.onBackPressed());
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Dont", (dialogInterface, i) -> {
        });
        alertDialog.show();
    }

}