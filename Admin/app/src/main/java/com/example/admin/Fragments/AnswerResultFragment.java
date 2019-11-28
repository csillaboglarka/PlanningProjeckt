package com.example.admin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Adapter.AnswerAdapter;
import com.example.admin.Classes.AnswerItem;
import com.example.admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AnswerResultFragment extends Fragment {

    private ArrayList<AnswerItem> mAnswers;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference answerRef = database.getReference().child("Answers");
    private RecyclerView.Adapter answerAdapter;
    private RecyclerView answerRecyclerView;
    private RecyclerView.LayoutManager answerLayoutManager;
    private String groupId,mQuestion;
    private TextView Questiontxt;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v;
        v = inflater.inflate(R.layout.fragment_answer_result, container, false);
        groupId = getArguments().getString("groupId");
        mQuestion=getArguments().getString("question");
        Questiontxt=v.findViewById(R.id.QuestionTextView);
        Questiontxt.setText(mQuestion);
        mAnswers = new ArrayList<>();
        // az adatbazisbol az osszes kerdesre adott valaszt kilistazza ugyanabbol a csoportbol es majd feltolti recycleviewt
        answerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String id = item.child("groupId").getValue().toString();
                    String sQuestion=item.child("question").getValue().toString();
                    if (id.equals(groupId) && sQuestion.equals(mQuestion)) {
                            String name = item.child("name").getValue().toString();
                            String  answer = item.child("answer").getValue().toString();

                            AnswerItem q1 = new AnswerItem(name,answer);
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