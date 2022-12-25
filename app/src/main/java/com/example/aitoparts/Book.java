package com.example.aitoparts;

public class Book {
    int id;
    String nama;
    String mobil;
    String tanggal;
    String jam;
    String namaMontir;

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getMobil() {
        return mobil;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJam() {
        return jam;
    }

    public String getNamaMontir() {
        return namaMontir;
    }

    public Book(int id, String nama, String mobil, String tanggal, String jam, String namaMontir) {
        this.id = id;
        this.nama = nama;
        this.mobil = mobil;
        this.tanggal = tanggal;
        this.jam = jam;
        this.namaMontir = namaMontir;
    }
}
