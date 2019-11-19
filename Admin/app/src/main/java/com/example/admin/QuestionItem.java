package com.example.admin;

public class QuestionItem {
    public String question;
    public String difficulty;
    public String groupId;

    public QuestionItem(String question) {
        this.question = question;

    }

    public void SetGroupId(String id) {
        this.groupId = id;
    }

    public void SetQuestion(String question) {
        this.question = question;
    }


    public String getQuestion() {
        return question;
    }


    public String getGroupId() {
        return groupId;
    }
}
