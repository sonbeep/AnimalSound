package com.example.animalsound.view.fragment;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.animalsound.App;
import com.example.animalsound.MTask;
import com.example.animalsound.R;
import com.example.animalsound.databinding.M002DetailFragmentBinding;
import com.example.animalsound.model.Animal;
import com.example.animalsound.view.adapter.DetailAdapter;
import com.example.animalsound.view.dialog.DetailInfoDialog;
import com.example.animalsound.viewmodel.CommonVM;
import com.example.animalsound.viewmodel.M002DetailViewModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class M002_Detail_Fragment extends BaseFragment<M002DetailFragmentBinding, M002DetailViewModel> {
    public static final String TAG = M002_Detail_Fragment.class.getName();
    private static final String KEY_SAVE_PHOTO = "KEY_SAVE_PHOTO";

    @Override
    protected Class<M002DetailViewModel> getClassVM() {
        return M002DetailViewModel.class;
    }

    @Override
    protected void initView() {
        Animal animal = (Animal) data;
        callback.makeBackArrow(App.getInstance().getStorage().typeAnimal);
        binding.vpAnimal.setAdapter(new DetailAdapter(context, App.getInstance().getStorage().listAnimal, v -> {
            clickView(v);
        }));
        binding.vpAnimal.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                Animal animal1 = App.getInstance().getStorage().listAnimal.get(position);
                binding.tvTitle.setText(animal1.getName());
            }
        });
        binding.vpAnimal.setCurrentItem(App.getInstance().getStorage().listAnimal.indexOf(animal));
    }
    @Override
    protected void clickView(View v) {
       if (v.getId() == R.id.iv_start) {
//            MediaPlayer.create(context, animal.getIdSound()).start();
            playSound((Animal) v.getTag());
        } else if (v.getId() == R.id.iv_search) {
//            searchImage((Animal) v.getTag());
           showInforDialog((Animal) v.getTag());
        }else if (v.getId() == R.id.iv_save) {
            saveToStorage((Animal) v.getTag());
        }
    }

    private void showInforDialog(Animal animal) {
        DetailInfoDialog dialog = new DetailInfoDialog(context, animal);
        dialog.show();
    }

    private void saveToStorage(Animal animal) {
        if (context.checkPermission(WRITE_EXTERNAL_STORAGE, 0, 0) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 101);
            return;
        }
        new MTask(KEY_SAVE_PHOTO, new MTask.OnMTaskCallBack() {
            @Override
            public Object execTask(String key, MTask task, Object data) {

                return viewModel.coppyPhotoToStorage(animal);
            }

            @Override
            public void completeTask(String key, Object value) {
                if (value == null){
                    Toast.makeText(context, "Could not save this photo", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context, "Photo is saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void playSound(Animal animal) {
        try {
            AssetFileDescriptor afd = App.getInstance().getAssets().openFd(animal.getIdSound());
            MediaPlayer player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchImage(Animal animal) {
        try {
            String word = URLEncoder.encode(animal.getName(), "UTF-8");
            Uri uri = Uri.parse("http://www.google.com/search?hl=en&q=" + word);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected M002DetailFragmentBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return M002DetailFragmentBinding.inflate(inflater, container, false);
    }
}
