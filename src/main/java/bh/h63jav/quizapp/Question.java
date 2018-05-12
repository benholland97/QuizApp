package bh.h63jav.quizapp;

import java.util.List;

public class Question {

    private String title, desc;
    private int imgQuestion, count;
    private String[] clues, answers;


    public Question(String title, String desc, String[] clues, String[] answers, int count, int isImgQuestion) {
        this.title = title;
        this.desc = desc;
        this.imgQuestion = isImgQuestion;
        this.clues = clues;
        this.answers = answers;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getImgQuestion() {
        return imgQuestion;
    }

    public String[] getClues() {
        return clues;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCount() {
        return count;
    }
}
