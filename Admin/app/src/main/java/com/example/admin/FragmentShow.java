package com.example.admin;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.LinearLayout;
import android.widget.Switch;
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
    private Button showAnswers;
    private Switch mybutton;
    private String groupId;
    private int postion;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference adminReference = database.getReference().child("Groups");
    static DatabaseReference questionsReference = database.getReference().child("Questions");


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v;
        v = inflater.inflate(R.layout.fragment_fragment_show, container, false);
        groupId = getArguments().getString("groupId");

        questionItems = new ArrayList<>();
        questionsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String txt = item.child("groupId").getValue().toString();

                    if (txt.equals(groupId)) {
                        String q = item.child("question").getValue().toString();
                        String p = item.child("active").getValue().toString();

                        if(p.equals("true")){
                            Log.i("fbdb",String.valueOf(p.equals(true)));
                        QuestionItem q1 = new QuestionItem(q,true);
                        questionItems.add(q1);
                        }
                        else {
                            QuestionItem q1 = new QuestionItem(q,false);
                            questionItems.add(q1);
                        }

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
        addNewQuestionButton = v.findViewById(R.id.AddNewQuestion);
      addNewQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  AddNewQuestionDialog dialog = new AddNewQuestionDialog();



                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Add new question");
                final EditText question = new EditText(getContext());
                final Switch active = new Switch(getContext());
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(question);
                layout.addView(active);

                builder.setView(layout);
                builder.setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String questionDesc = question.getText().toString();
                        Log.i("fbdb",String.valueOf(active.isChecked()));
                        if(active.isChecked()){
                            Boolean active = true;
                            applyQuestion(questionDesc,active);
                            QuestionItem q= new QuestionItem(questionDesc,active);
                             FirebaseDataHelper.Instance.InsertQuestion(q,groupId);}
                        else {
                            Boolean active = false;
                            applyQuestion(questionDesc,active);
                            QuestionItem q= new QuestionItem(questionDesc,active);
                            FirebaseDataHelper.Instance.InsertQuestion(q,groupId);
                        }

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






        return v;
    }


    public void applyQuestion(String question,Boolean active) {
        questionItems.add(new QuestionItem(question,active));
        questionAdapter.notifyItemInserted(questionItems.size());
    }

    @Override
    public void applyQuestion(String question) {

    }

    @Override
    public void applyQuestionModification(String question, int pos) {

    }


}