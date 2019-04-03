package com.appshop162.sqlite;

import java.util.Random;

public class ListElement {
    public String text;
    public ImageColor imageColor;
    public long id;
    public enum ImageColor{ ORANGE, GREY }

    public ListElement() {
        id = 0;
        Random random = new Random();
        String numbers = "0123456789";
        text = "";
        imageColor = ImageColor.values()[random.nextInt(ImageColor.values().length)];
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(numbers.length());
            text += numbers.substring(index, index + 1);
        }
    }

    public ListElement(ImageColor imageColor, String text, long id) {
        this.imageColor = imageColor;
        this.text = text;
        this.id = id;
    }

    public int getDrawable() {
        switch (imageColor) {
            case GREY:
                return R.drawable.plus_no;
            case ORANGE:
                return R.drawable.plus;
        }
        return R.drawable.plus;
    }
}
