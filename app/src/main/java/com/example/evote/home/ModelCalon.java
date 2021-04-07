package com.example.evote.home;

public class ModelCalon {
    public String nourut;
    public String nama;
    public String prodi;
    public String angkatan;
    public String foto;
    public String detail2;
    public String visi;
    public String nim;

    public ModelCalon() {

    }

    public ModelCalon(String nourut, String nim, String nama, String prodi, String angkatan, String foto, String detail2, String visi, String misi) {
        this.nourut = nourut;
        this.nama = nama;
        this.prodi = prodi;
        this.angkatan = angkatan;
        this.foto = foto;
        this.detail2 = detail2;
        this.visi = visi;
        this.misi = misi;
        this.nim = nim;

    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNourut() {
        return nourut;
    }

    public void setNourut(String nourut) {
        this.nourut = nourut;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDetail2() {
        return detail2;
    }

    public void setDetail2(String detail2) {
        this.detail2 = detail2;
    }

    public String getVisi() {
        return visi;
    }

    public void setVisi(String visi) {
        this.visi = visi;
    }

    public String getMisi() {
        return misi;
    }

    public void setMisi(String misi) {
        this.misi = misi;
    }

    public String misi;

}

