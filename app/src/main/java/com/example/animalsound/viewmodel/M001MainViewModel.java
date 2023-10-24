package com.example.animalsound.viewmodel;

import com.example.animalsound.App;
import com.example.animalsound.model.Animal;

public class M001MainViewModel extends BaseViewModel{
    public void initData(String typeAnimal) {
        App.getInstance().getStorage().listAnimal.clear();

        try {
            String [] path =App.getInstance().getAssets().list(typeAnimal);
            for (String item : path){
                String name = item.replace(".png", "");
                Animal animal = new Animal("sounds/"+typeAnimal+"/"+name+".mp3", typeAnimal+"/"+item, name);
                App.getInstance().getStorage().listAnimal.add(animal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
