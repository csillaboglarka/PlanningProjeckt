package com.example.pokeruser.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.pokeruser.Adapters.QuestionAdapter;
import com.example.pokeruser.Classes.QuestionItem;
import com.example.pokeruser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class FragmentShow extends Fragment {

    private RecyclerView questionsRecyclerView;
    private QuestionAdapter questionAdapter;
    private RecyclerView.LayoutManager questionLayoutManager;
    private ArrayList<QuestionItem> questionItems;
    private Button showAnswers;
    private String groupId;
    private String uName;
    private TextView name;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference questionsReference = database.getReference().child("Questions");

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v;
        v = inflater.inflate(R.layout.fragment_fragment_show, container, false);
        showAnswers = v.findViewById(R.id.result);
        groupId = getArguments().getString("groupId");
        uName = getArguments().getString("Name");
        name=v.findViewById(R.id.currentUser);
        name.setText(uName);
        //Az eredmenyeket tudjuk megnezni
        showAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                Fragment f = new AnswerResultFragment();
                fr.replace(R.id.fragment_container,f);
                Bundle args = new Bundle();
                args.putString("groupId", groupId);
                f.setArguments(args);
                fr.commit();
            }
        });


        questionItems = new ArrayList<>();
        //Betolti az osszes aktiv kerdest
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



