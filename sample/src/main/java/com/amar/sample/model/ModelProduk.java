package com.amar.sample.model;

public class ModelProduk {

    private String nama, npm, nohp;
    private int gambar;

    public ModelProduk(String nama, String npm, String nohp, int gambar) {
        this.nama = nama;
        this.npm = npm;
        this.nohp = nohp;
        this.gambar = gambar;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }
}
