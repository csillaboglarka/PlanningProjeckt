package com.example.pokeruser.Classes;


    public class QuestionItem {
        public String question;
        public String groupId;
        public String name;

        public QuestionItem(String question) {
            this.question = question;

        }

        public QuestionItem(String question,String groupId,String name) {
            this.question = question;
            this.groupId=groupId;
            this.name=name;

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


