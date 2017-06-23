package com.filip.lifesaverandroid.model;


import io.realm.RealmObject;

public class NotificationEntry extends RealmObject implements BaseEntity {

    private String title;

    private String text;


    public NotificationEntry() {

    }

    public NotificationEntry(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
