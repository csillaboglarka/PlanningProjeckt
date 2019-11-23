package com.example.pokeruser.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.pokeruser.Classes.QuestionItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseDataHelper {
    public static ArrayList<QuestionItem> Kerdesek= new ArrayList<>();
    public String session;
    public FirebaseDataHelper() {

    }


    public static class Instance {
        static FirebaseDatabase database = FirebaseDatabase.getInstance();
        static DatabaseReference adminReference = database.getReference().child("Groups");
        static DatabaseReference questionsReference = database.getReference().child("Questions");
        static DatabaseReference answerReference = database.getReference().child("Answers");

        public static void QuestionsUpdate(ArrayList<QuestionItem> q) {
            Kerdesek.addAll(q);
            if (Kerdesek.isEmpty()) {Log.i("fbdb","ures");}
            Log.i("fbdb","nem ures");
        }
        public static ArrayList<QuestionItem> GetQuestionsForSession(final String session) {


            questionsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        String txt = item.child("groupId").getValue().toString();

                        if (txt.equals(session)) {
                            String q = item.child("question").getValue().toString();


                            QuestionItem q1 = new QuestionItem(q);
                            Kerdesek.add(q1);
                           QuestionsUpdate(Kerdesek);
                        }
                      //   QuestionsUpdate(questions);

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            return Kerdesek;
        }


    }

}