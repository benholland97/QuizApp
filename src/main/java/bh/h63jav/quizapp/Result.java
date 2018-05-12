package bh.h63jav.quizapp;

import static java.lang.Math.round;

public class Result {

    private User user;
    private String catID;
    private int score, maxAvailable;
    private double percentage;

    public Result() {

    }

    public Result(User user, String catID, int score, int maxAvailable) {
        this.user = user;
        this.catID = catID;
        this.score = score;
        this.maxAvailable = maxAvailable;
    }

    public int getScore() {
        return score;
    }

    public int getMaxAvailable() {
        return maxAvailable;
    }

    public double getPercentage() {
        return round(((double)score/(double)maxAvailable)*100);
    }
}
