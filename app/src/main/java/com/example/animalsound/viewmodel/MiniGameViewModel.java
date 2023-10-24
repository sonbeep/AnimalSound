package com.example.animalsound.viewmodel;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.animalsound.model.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MiniGameViewModel extends BaseViewModel{
    private final List<Animal> listAnimal = new ArrayList<>();
    private MutableLiveData<Integer> score = new MutableLiveData<>(0);
    private int index = 0;
    private Animal animal;

    public void initAnimalList(List<Animal> data) {
        listAnimal.clear();
        listAnimal.addAll(data);
        Collections.shuffle(listAnimal);
    }

    public void initScore(int data) {
        score.setValue(data);
    }

    public MutableLiveData<Integer> getScore() {
        return score;
    }
    public String[] initCard(){
        animal = listAnimal.get(index);
        List<Animal> tmpList = new ArrayList<>(listAnimal);
        tmpList.remove(animal);
        Collections.shuffle(tmpList);
        String textA;
        String textB;
        if (new Random().nextBoolean()){
            textA = "A: "+animal.getName();
            textB = "B: "+tmpList.get(0).getName();
        }else {
            textA = "B: "+tmpList.get(0).getName();
            textB = "A: "+animal.getName();
        }
        return new String[]{textA, textB};
    }
    public boolean checkAnswer(String ans) {
        if (ans.replace("A: ", "")
                .replace("B: ","")
                .equals(animal.getName())){
            score.setValue(score.getValue() + 1);

            index++;
            if (index >= listAnimal.size()){
                index=0;
            }
            return true;
        }
        return false;
    }

    public Animal getAnimal() {
        return animal;
    }
}
