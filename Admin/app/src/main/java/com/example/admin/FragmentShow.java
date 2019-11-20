package com.example.admin;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.app.AppCompatActivity;




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
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;


public class FragmentShow extends Fragment implements AddQuestionDialogListener  {

    private RecyclerView questionsRecyclerView;
    private QuestionAdapter questionAdapter;
    private RecyclerView.LayoutManager questionLayoutManager;
    private FloatingActionButton addNewQuestionButton;
    public ArrayList<QuestionItem> questionItems;
    private Button createSessionButton;
    private String groupId;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v = inflater.inflate(R.layout.fragment_fragment_show, container, false);
        groupId = getArguments().getString("groupId");
        questionItems = new ArrayList<>();
        addNewQuestionButton = v.findViewById(R.id.AddNewQuestion);
      addNewQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  AddNewQuestionDialog dialog = new AddNewQuestionDialog();



                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Add new question");
                final EditText question = new EditText(getContext());
                builder.setView(question);
                builder.setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String questionDesc = question.getText().toString();
                        applyQuestion(questionDesc);
                        QuestionItem q= new QuestionItem(questionDesc);
                        FirebaseDataHelper.Instance.InsertQuestion(q,groupId);
                    }
                });

                builder.show();
            }

        });
        this.questionsRecyclerView =v.findViewById(R.id.questionListRecyclerView);

        questionLayoutManager = new LinearLayoutManager(getActivity());

        questionAdapter = new QuestionAdapter(questionItems);
          questionsRecyclerView.setLayoutManager(questionLayoutManager);
            questionsRecyclerView.setAdapter(questionAdapter);
            questionsRecyclerView.setHasFixedSize(true);
          // questionAdapter.setOnItemClickListener(getActivity().);




        return v;
    }

    @Override
    public void applyQuestion(String question) {
        questionItems.add(new QuestionItem(question));
        questionAdapter.notifyItemInserted(questionItems.size());
    }

    @Override
    public void applyQuestionModification(String question, int pos) {

    }


}