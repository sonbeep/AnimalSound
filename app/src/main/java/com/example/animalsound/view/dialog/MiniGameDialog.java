package com.example.animalsound.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.animalsound.App;
import com.example.animalsound.CommonUtils;
import com.example.animalsound.R;
import com.example.animalsound.databinding.MiniGameBinding;
import com.example.animalsound.model.Animal;
import com.example.animalsound.viewmodel.MiniGameViewModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MiniGameDialog extends Dialog implements View.OnClickListener {

    private static final String KEY_SCORE = "pref";
    private final MiniGameBinding binding;
    private final Context context;
    private final MiniGameViewModel viewModel;

    public MiniGameDialog(@NonNull Context context, ViewModelStoreOwner owner, List<Animal> listAnimal) {
        super(context, R.style.Theme_Dialog);
        viewModel = new ViewModelProvider(owner).get(MiniGameViewModel.class);
        viewModel.initAnimalList(listAnimal);
        this.context = context;
        binding = MiniGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView(owner);
    }

    private void initView(ViewModelStoreOwner owner) {
        binding.ivBack.setOnClickListener(this);
        binding.tvCard.setOnClickListener(this);
        binding.tvA.setOnClickListener(this);
        binding.tvB.setOnClickListener(this);
        initCard();
        String txtScore = CommonUtils.getInstance().getPref(KEY_SCORE);
        if (txtScore != null) {
            viewModel.initScore(Integer.parseInt(txtScore));
        }
        viewModel.getScore().observe((LifecycleOwner) owner, score -> {
            binding.tvScore.setText(score);
            CommonUtils.getInstance().savePref(KEY_SCORE, score + "");
        });
    }

    private void initCard() {
        String[] txtArr = viewModel.initCard();
        binding.tvA.setText(txtArr[0]);
        binding.tvB.setText(txtArr[1]);

        int lenA = txtArr[0].length();
        int lenB = txtArr[1].length();
        String max = lenA > lenB ? txtArr[0] : txtArr[1];

        Rect bounds = new Rect();
        Paint textPaint = binding.tvA.getPaint();
        textPaint.getTextBounds(max, 0, max.length(), bounds);
        int width = bounds.width();

        binding.tvA.setWidth(width + 150);
        binding.tvB.setWidth(width + 150);
    }

    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        if (view.getId() == R.id.iv_back) {
            dismiss();
        } else if (view.getId() == R.id.tv_card) {
            showCardAnimal();
        } else if ((view.getId() == R.id.tv_a || view.getId() == R.id.tv_b)) {
            checkAnswer(((TextView) view).getText().toString());
        }
    }

    private void checkAnswer(String ans) {
        boolean rs = viewModel.checkAnswer(ans);
        if (rs) {
            initCard();
        } else {
            Toast.makeText(context, "Wrong answer", Toast.LENGTH_SHORT).show();
        }
    }


    private void showCardAnimal() {
        Toast toast = new Toast(context);
        ImageView ivAnimal = new ImageView(context);
        ivAnimal.setLayoutParams(new LinearLayout.LayoutParams(100, 100));

        try {
            InputStream in1 = App.getInstance().getAssets().open(viewModel.getAnimal().getIdPhoto());
            ivAnimal.setImageBitmap(BitmapFactory.decodeStream(in1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        toast.setView(ivAnimal);

        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 10);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
