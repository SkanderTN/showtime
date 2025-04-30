package com.example.spectacleapp;

import java.util.List;

public class AllSpectaclesListHolder {
    private static List<Spectacle> spectacles;

    public static void setSpectacles(List<Spectacle> list) {
        spectacles = list;
    }

    public static List<Spectacle> getSpectacles() {
        return spectacles;
    }
}
