package com.example.myapplication.Questions;

/**
 * Created by swain on 3/1/18.
 */

public class QuestionModel {
    private String question;
    private boolean check;

    public QuestionModel (String question, Boolean check) {
        this.question = question;
        this.check = check;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

}
