package org.example.model;

public class Reviews {
    private String name;
    private String date;
    private int score;
    private String text;

    public Reviews(String name, String date, int score, String text) {
        this.name = name;
        this.date = date;
        this.score = score;
        this.text = text;
    }

    public int getScore() {
        return score;
    }
}
