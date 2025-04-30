package com.example.spectacleapp;


public class Spectacle {
    public String titre;
    public String date;
    public String time;
    public String lieu;
    public String duree;
    public int imageRes;

    public Spectacle(String titre, String date, String time, String lieu, String duree, int imageRes) {
        this.titre = titre;
        this.date = date;
        this.time = time;
        this.lieu = lieu;
        this.duree = duree;
        this.imageRes = imageRes;
    }
}
