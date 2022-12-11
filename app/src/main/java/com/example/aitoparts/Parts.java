package com.example.aitoparts;

public class Parts {

    private int id;
    private String name;
    private int price;
    private int inStock;
    private String imageLink;

    public Parts(int id,String name, int price, int inStock, String imageLink) {
        this.id = id;
        this.name=name;
        this.price = price;
        this.inStock = inStock;
        this.imageLink = imageLink;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getInStock() {
        return inStock;
    }

    public String getImageLink() {
        return imageLink;
    }
}
