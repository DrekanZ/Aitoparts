package com.example.aitoparts;

public class Paket {
    String id;
    String namaPaket;
    String harga;

    public Paket(String id, String namaPaket, String harga) {
        this.id = id;
        this.namaPaket = namaPaket;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public String getNamaPaket() {
        return namaPaket;
    }

    public String getHarga() {
        return harga;
    }
}
