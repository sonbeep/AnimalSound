package com.example.animalsound.model;

import java.util.Objects;

public class Animal {
    private final String idSound;
    private final String idPhoto;
    private final String name;

    public Animal(String idSound, String idPhoto, String name) {
        this.idSound = idSound;
        this.idPhoto = idPhoto;
        this.name = name;
    }

    public String getIdSound() {
        return idSound;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return idPhoto == animal.idPhoto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPhoto);
    }
}
