package com.example.pokeruser.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.pokeruser.Adapters.AnswerAdapter;
import com.example.pokeruser.Adapters.QuestionAdapter;
import com.example.pokeruser.Classes.AnswerItem;
import com.example.pokeruser.Classes.QuestionItem;
import com.example.pokeruser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AnswerResultFragment extends Fragment {

    public ArrayList<AnswerItem> mAnswers;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference answerRef = database.getReference().child("Answers");
    RecyclerView.Adapter answerAdapter;
    RecyclerView answerRecyclerView;
    RecyclerView.LayoutManager answerLayoutManager;
    public String groupId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v;
        v = inflater.inflate(R.layout.fragment_answer_result, container, false);
        groupId = getArguments().getString("groupId");
        mAnswers = new ArrayList<>();
        answerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String txt = item.child("groupId").getValue().toString();

                    if (txt.equals(groupId)) {
//
                            String name = item.child("name").getValue().toString();
                            String  answer = item.child("answer").getValue().toString();
                            String question = item.child("question").getValue().toString();
//
                           AnswerItem q1 = new AnswerItem(name,answer,question);
                            mAnswers.add(q1);

                            }



                }

                answerAdapter = new AnswerAdapter(mAnswers);
                answerRecyclerView = v.findViewById(R.id.questionListRecyclerView);
                answerLayoutManager = new LinearLayoutManager(getActivity());
                answerRecyclerView.setLayoutManager(answerLayoutManager);
                answerRecyclerView.setAdapter(answerAdapter);
                answerRecyclerView .setHasFixedSize(true);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return v;
    }
}