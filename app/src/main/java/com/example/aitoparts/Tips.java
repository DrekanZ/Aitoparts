package com.example.aitoparts;

public class Tips {
    private int id;
    private String imageLink;
    private String text;
    private String title;

    public Tips(int id,String title, String imageLink, String text) {
        this.id = id;
        this.imageLink = imageLink;
        this.text = text;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {return title; }

    public String getImageLink() {
        return imageLink;
    }

    public String getText() {
        return text;
    }
}
