package com.mindhub.homebanking.utils;

public final class AccountUtils {
    private AccountUtils() {
    }

    public static long getRandomNumber() {
        return (long) ((Math.random() * (100000000 - 1)) + 1);

    }
}
