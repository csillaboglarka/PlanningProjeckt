package com.example.admin;

public class QuestionItem {
    public String question;
    public String groupId;
    public boolean active;

    public QuestionItem(String question,boolean active) {
        this.question = question;
        this.active= active;

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
