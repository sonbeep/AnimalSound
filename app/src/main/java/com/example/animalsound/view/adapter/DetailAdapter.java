package com.example.animalsound.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.animalsound.R;
import com.example.animalsound.model.Animal;

import java.util.ArrayList;

public class DetailAdapter extends PagerAdapter {
    private final Context context;
    private final ArrayList<Animal> listAnimal;
    private View.OnClickListener event;

    public DetailAdapter(Context context, ArrayList<Animal> listAnimal,View.OnClickListener event) {
        this.context = context;
        this.listAnimal = listAnimal;
        this.event = event;
    }

    @Override
    public int getCount() {
        return listAnimal.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Animal item = listAnimal.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_animal, container, false);
        ImageView ivAnimal = view.findViewById(R.id.iv_animal);
        Glide.with(context).load(Uri.parse("file:///android_asset/"+item.getIdPhoto())).into(ivAnimal);
        Animal animal = listAnimal.get(position);
        View.OnClickListener onClick = view1 -> {
            view1.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
            event.onClick(view1);
            view1.setTag(animal);
        };
        ImageView ivStart = view.findViewById(R.id.iv_start);
        ImageView ivSave = view.findViewById(R.id.iv_save);
        ImageView ivSearch = view.findViewById(R.id.iv_search);

        ivStart.setTag(animal);
        ivSave.setTag(animal);
        ivSearch.setTag(animal);

        ivStart.setOnClickListener(onClick);
        ivSave.setOnClickListener(onClick);
        ivSearch.setOnClickListener(onClick);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
