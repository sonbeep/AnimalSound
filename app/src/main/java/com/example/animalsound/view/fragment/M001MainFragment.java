package com.example.animalsound.view.fragment;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.animalsound.App;
import com.example.animalsound.R;
import com.example.animalsound.databinding.M001MainFragmentBinding;
import com.example.animalsound.model.Animal;
import com.example.animalsound.view.act.MainActivity;
import com.example.animalsound.view.adapter.AnimalAdapter;
import com.example.animalsound.view.dialog.MiniGameDialog;
import com.example.animalsound.viewmodel.M001MainViewModel;

import java.util.Locale;

public class M001MainFragment extends BaseFragment<M001MainFragmentBinding, M001MainViewModel> {
    public static final String TAG = M001MainFragment.class.getName();
    private TextToSpeech tts;
    @Override
    protected Class<M001MainViewModel> getClassVM() {
        return M001MainViewModel.class;
    }

    @Override
    protected void initView() {
        callback.enableDrawer();
        initData(MainActivity.TYPE_BIRD);
        initAnimalView();
        binding.tvMiniGame.setOnClickListener(this);
    }

    private void initData(String typeAnimal) {
        binding.tvTitle.setText(typeAnimal.toUpperCase());
        viewModel.initData(typeAnimal);
    }

    @Override
    protected void clickView(View v) {
        if (v.getId() == R.id.tv_mini_game){
            showMiniGame();
            return;
        }
        Animal tag = (Animal) v.getTag();

        tts = new TextToSpeech(context, i -> {
            if (i != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.UK);
            }
        });
        gotoDetailScreen(tag);
    }

    private void showMiniGame() {
        MiniGameDialog dialog = new MiniGameDialog(context, this, App.getInstance().getStorage().listAnimal);
        dialog.show();
    }

    private void gotoDetailScreen(Animal animal) {
       tts.speak(animal.getName(), TextToSpeech.QUEUE_FLUSH, null);
       App.getInstance().getStorage().typeAnimal = binding.tvTitle.getText().toString();
        callback.showFragment(M002_Detail_Fragment.TAG, animal, true);
    }

    private void initAnimalView() {
        binding.rvAnimal.setAdapter(new AnimalAdapter(context,
                App.getInstance().getStorage().listAnimal, view -> clickView(view)));
    }

    @Override
    protected M001MainFragmentBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return M001MainFragmentBinding.inflate(inflater, container, false);
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    public void showListAnimal(String typeAnimal) {
        initData(typeAnimal);
        Log.d(TAG, typeAnimal);
        initAnimalView();
    }
}
