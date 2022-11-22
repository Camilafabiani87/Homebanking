package com.mindhub.homebanking.utils;

public final class CardUtils {

    private CardUtils() {
    }
    public static String getNumberCard() {
        return (int) (Math.random() * (10000 - 1000) + 1000) + " " + (int) (Math.random() * (10000 - 1000) + 1000) + " " + (int) (Math.random() * (10000 - 1000) + 1000) + " " + (int) (Math.random() * (10000 - 1000) + 1000);
    }
    public static int getNumberCvv() {
        return (int) (Math.random() * 1000 - 100) + 100;
    }


}
