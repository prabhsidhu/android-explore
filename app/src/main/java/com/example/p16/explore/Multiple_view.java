package com.example.p16.explore;

import java.io.Serializable;

public class Multiple_view implements Serializable {
    private String Name;
    private String Description;
    private String Image;
    private String Key;



    public Multiple_view() {
    }

    public Multiple_view(String name, String description, String image,String key) {
        Name = name;
        Description = description;
        Image = image;
        Key = key;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
