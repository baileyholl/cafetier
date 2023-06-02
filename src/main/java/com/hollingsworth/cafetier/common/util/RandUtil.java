package com.hollingsworth.cafetier.common.util;

public class RandUtil {

    // Generates a random number between min and max, inclusive
    public static int inclusiveRange(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
