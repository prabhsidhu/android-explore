package com.example.p16.explore;

import java.io.Serializable;
import java.util.Date;

public class Multiple_view implements Serializable {
    private String Name;
    private String Description;
    private String Image;
    private String Key;
    private String Date;
    private String Time;
    private String Place;
    private String Address;
//    private Long Date;
      private String Menu;
    private String Register;
    private String Contact;
    private Boolean Bookmark;


    public Multiple_view() {
    }

    public Multiple_view(String menu, String name, String description, String image,String key, String time, String address, String place, String register, String contact, String date, Boolean bookmark) {
        Name = name;
        Description = description;
        Image = image;
        Key = key;
        Date = date;
        Time  = time;
        Address = address;
        Place = place;
        Register = register;
        Contact = contact;
        Menu = menu;
        Bookmark = bookmark;

    }

    public Boolean getBookmark() {
        return Bookmark;
    }

    public void setBookmark(Boolean bookmark) {
        Bookmark = bookmark;
    }

    public String getMenu() {
        return Menu;
    }

    public void setMenu(String menu) {
        Menu = menu;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }


    public String getRegister() {
        return Register;
    }

    public void setRegister(String register) {
        Register = register;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
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
