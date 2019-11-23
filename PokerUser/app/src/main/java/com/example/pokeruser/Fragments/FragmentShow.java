package com.example.pokeruser.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokeruser.Adapters.QuestionAdapter;
import com.example.pokeruser.Classes.QuestionItem;
import com.example.pokeruser.Database.FirebaseDataHelper;
import com.example.pokeruser.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentShow extends Fragment {

    private RecyclerView questionsRecyclerView;
    public QuestionAdapter questionAdapter;
    private RecyclerView.LayoutManager questionLayoutManager;
    private FloatingActionButton addNewQuestionButton;
    public ArrayList<QuestionItem> questionItems;
    private Button showAnswers;
    private String groupId;
    private String uName;
    private TextView name;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();

    static DatabaseReference questionsReference = database.getReference().child("Questions");
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v;
        v = inflater.inflate(R.layout.fragment_fragment_show, container, false);

       groupId = getArguments().getString("groupId");
        uName = getArguments().getString("Name");
        name=v.findViewById(R.id.currentUser);
        name.setText(uName);


        questionItems = new ArrayList<>();
        questionsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String txt = item.child("groupId").getValue().toString();

                    if (txt.equals(groupId)) {
                        if(item.child("active").getValue().toString().equals("true")) {
                            String q = item.child("question").getValue().toString();
                            QuestionItem q1 = new QuestionItem(q,groupId,name.getText().toString());
                            questionItems.add(q1);}
                    }


                }

                questionAdapter = new QuestionAdapter(questionItems);
                questionsRecyclerView = v.findViewById(R.id.questionListRecyclerView);
                questionLayoutManager = new LinearLayoutManager(getActivity());
                questionsRecyclerView.setLayoutManager(questionLayoutManager);
                questionsRecyclerView.setAdapter(questionAdapter);
                questionsRecyclerView.setHasFixedSize(true);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }
}



