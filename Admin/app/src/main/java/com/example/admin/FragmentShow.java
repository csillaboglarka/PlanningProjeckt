package com.example.admin;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.admin.QuestionAdapter;
import com.example.admin.Interfaces.AddQuestionDialogListener;
import com.example.admin.Interfaces.OnItemClickListener;
import com.example.admin.QuestionItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class FragmentShow extends Fragment  {
    private RecyclerView questionsRecyclerView;
    private QuestionAdapter questionAdapter;
    private RecyclerView.LayoutManager questionLayoutManager;
    private FloatingActionButton addNewQuestionButton;
    public ArrayList<QuestionItem> questionItems;
    private Button createSessionButton;
    String groupId = "55666";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v = inflater.inflate(R.layout.fragment_fragment_show, container, false);
        questionItems = new ArrayList<>();
        addNewQuestionButton = v.findViewById(R.id.AddNewQuestion);
      addNewQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewQuestionDialog dialog = new AddNewQuestionDialog();
                dialog.show(getFragmentManager(),"Add new question");





            }
        });
        this.questionsRecyclerView =v.findViewById(R.id.questionListRecyclerView);
          //  questionLayoutManager = new LinearLayoutManager(this);
       // questionLayoutManager = new LinearLayoutManager(getActivity());
        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        questionAdapter = new QuestionAdapter(questionItems);
         //   questionsRecyclerView.setLayoutManager(questionLayoutManager);
            questionsRecyclerView.setAdapter(questionAdapter);
            questionsRecyclerView.setHasFixedSize(true);
          //  questionAdapter.setOnItemClickListener(this);


        return v;
    }





}