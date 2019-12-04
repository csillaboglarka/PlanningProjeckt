package com.example.admin.Fragments;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.Switch;
import com.example.admin.Database.FirebaseDataHelper;
import com.example.admin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.admin.Adapter.QuestionAdapter;
import com.example.admin.Classes.QuestionItem;
import java.util.ArrayList;

public class FragmentShow extends Fragment   {

    private RecyclerView questionsRecyclerView;
    private QuestionAdapter questionAdapter;
    private RecyclerView.LayoutManager questionLayoutManager;
    private FloatingActionButton addNewQuestionButton;
    private ArrayList<QuestionItem> questionItems;
    private String groupId;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference questionsReference = database.getReference().child("Questions");
    private View llProgressBar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v;
        v = inflater.inflate(R.layout.fragment_fragment_show, container, false);
        llProgressBar= v.findViewById(R.id.llProgressBar);
        llProgressBar.setVisibility(View.VISIBLE);
        groupId = getArguments().getString("groupId");

        questionItems = new ArrayList<>();
        questionsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionItems.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String txt = item.child("groupId").getValue().toString();

                    if (txt.equals(groupId)) {
                        String q = item.child("question").getValue().toString();
                        String p = item.child("active").getValue().toString();

                        if(p.equals("true")) {
                        QuestionItem q1 = new QuestionItem(q,true);
                        questionItems.add(q1);
                        }
                        else {
                            QuestionItem q1 = new QuestionItem(q,false);
                            questionItems.add(q1);
                        }
                    }

                }

                questionAdapter = new QuestionAdapter(questionItems,groupId,getFragmentManager(),getContext());
                questionsRecyclerView = v.findViewById(R.id.questionListRecyclerView);
                questionLayoutManager = new LinearLayoutManager(getActivity());
                questionsRecyclerView.setLayoutManager(questionLayoutManager);
                questionsRecyclerView.setAdapter(questionAdapter);
                questionsRecyclerView.setHasFixedSize(true);
                llProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        addNewQuestionButton = v.findViewById(R.id.AddNewQuestion);
        addNewQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.MyDialogTheme);
                builder.setTitle("Add new question");
                final EditText question = new EditText(getContext());
                question.setTextColor(Color.parseColor("#FFFFFF"));
                question.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(question);
                builder.setView(layout);
                builder.setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String questionDesc = question.getText().toString();
                        applyQuestion(questionDesc,false);
                        QuestionItem q= new QuestionItem(questionDesc,false);
                        FirebaseDataHelper.Instance.InsertQuestion(q,groupId);}



                });

                builder.show();

            }

        });
        this.questionsRecyclerView =v.findViewById(R.id.questionListRecyclerView);
        questionLayoutManager = new LinearLayoutManager(getActivity());
        questionAdapter = new QuestionAdapter(questionItems,groupId,getFragmentManager(),getContext());
        questionsRecyclerView.setLayoutManager(questionLayoutManager);
        questionsRecyclerView.setAdapter(questionAdapter);
        questionsRecyclerView.setHasFixedSize(true);

        return v;
    }


    private void applyQuestion(String question,Boolean active) {
        questionItems.add(new QuestionItem(question,active));
        questionAdapter.notifyItemInserted(questionItems.size());
    }


}