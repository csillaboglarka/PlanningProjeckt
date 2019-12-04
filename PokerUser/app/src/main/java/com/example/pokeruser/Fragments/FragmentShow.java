package com.example.pokeruser.Fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pokeruser.Classes.AnswerItem;
import com.example.pokeruser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentShow extends Fragment {

    private Button btnShowAnswers;
    private String groupId, uName, answer, question;
    private TextView name;
    private CardView first,second,third,forth,fivth,sixth;
    private Button btn_Answer;
    private TextView textViewQuestion;
    private static boolean check,exists;
    private CardView cardViewLastChecked;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference questionsReference = database.getReference().child("Questions");
    private static DatabaseReference answerReference = database.getReference().child("Answers");
    private View llProgressBar, view_cards;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v;
        v = inflater.inflate(R.layout.fragment_fragment_show, container, false);
        llProgressBar= v.findViewById(R.id.llProgressBar);
        llProgressBar.setVisibility(View.VISIBLE);
        check=false;
        view_cards =v.findViewById(R.id.cards);
        groupId = getArguments().getString("groupId");
        uName = getArguments().getString("Name");
        name = v.findViewById(R.id.currentUser);
        textViewQuestion = v.findViewById(R.id.QuestionTextView);
        InitializeButtons(v);
        InitializeOnClickListeners();
        name.setText(uName);
        getQuestion();

        return v;
    }
    private void InitializeButtons(View v) {
        btnShowAnswers = v.findViewById(R.id.result);
        btn_Answer = v.findViewById(R.id.AnswerButton);
        first=v.findViewById(R.id.one);
        second=v.findViewById(R.id.five);
        third=v.findViewById(R.id.ten);
        forth=v.findViewById(R.id.twelve);
        fivth=v.findViewById(R.id.fivty);
        sixth=v.findViewById(R.id.oneHundred);


    }
    private void  InitializeOnClickListeners() {
        //elkuldi a valaszt, ellenorzi ha nem valaszt semmit vagy mar valaszolt arra a kerdesre
        btn_Answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                if(answer==null) {
                    Toast.makeText(v.getContext(),"Please chose one card",Toast.LENGTH_SHORT).show();
                }
                else {
                    exists = false;
                    llProgressBar.setVisibility(View.VISIBLE);
                    answerReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot item : dataSnapshot.getChildren()) {
                                if (item.child("name").getValue().toString().equals(uName) &&
                                        item.child("question").getValue().toString().equals(question) &&
                                        item.child("groupId").getValue().toString().equals(groupId)) {
                                        exists = true; //mar valaszolt

                                }
                            }
                            if (!exists) {
                                //akkor ha nem valaszolt letrehoz egy uj valaszt
                                AnswerItem my = new AnswerItem(uName, answer, groupId, question);
                                String newKey = answerReference.push().getKey();
                                if (newKey != null) {
                                    answerReference.child(newKey).setValue(my);
                                    textViewQuestion.setText(R.string.success_answer);
                                    view_cards.setVisibility(View.GONE);
                                    btn_Answer.setVisibility(View.GONE);
                                    llProgressBar.setVisibility(View.GONE);
                                    btnShowAnswers.setVisibility(View.VISIBLE);
                                }

                            } else {
                                llProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), R.string.answer_exists, Toast.LENGTH_SHORT).show();

                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }
        });

        //Az eredmenyeket tudjuk megnezni
        btnShowAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                Fragment f = new AnswerResultFragment();
                fr.addToBackStack(null);
                fr.replace(R.id.fragment_container, f);
                Bundle args = new Bundle();
                args.putString("question",question);
                args.putString("groupId", groupId);
                f.setArguments(args);
                fr.commit();
            }
        });
        // a kartyakat nezi
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check) {
                    cardViewLastChecked.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#181825")));
                    if(first.getBackgroundTintList().equals(ColorStateList.valueOf(Color.parseColor("#181825")))){
                    first.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2132B")));

                    cardViewLastChecked =first;
                    check=true;
                    TextView val=first.findViewById(R.id.value);
                    answer=val.getText().toString();


                    }
                    else {
                        first.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#181825")));
                        cardViewLastChecked =null;
                        check=false;
                        answer=null;
                    }
                }
                else {
                    TextView val=first.findViewById(R.id.value);
                    answer=val.getText().toString();
                    check=true;
                    first.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2132B")));
                    cardViewLastChecked =first;
                }

            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check) {
                    cardViewLastChecked.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#181825")));
                    if(second.getBackgroundTintList().equals(ColorStateList.valueOf(Color.parseColor("#181825")))){
                        second.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2132B")));
                        cardViewLastChecked =second;
                        check=true;
                        TextView val=second.findViewById(R.id.value2);
                        answer=val.getText().toString();
                    }
                    else {
                        second.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#181825")));
                        cardViewLastChecked =null;
                        check=false;
                        answer=null;
                    }
                }
                else {
                    check=true;
                    second.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2132B")));
                    cardViewLastChecked =second;
                    TextView val=second.findViewById(R.id.value2);
                    answer=val.getText().toString();
                }
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check) {
                    cardViewLastChecked.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#181825")));
                    if(third.getBackgroundTintList().equals(ColorStateList.valueOf(Color.parseColor("#181825")))){
                        third.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2132B")));
                        cardViewLastChecked =third;
                        check=true;
                        TextView val=third.findViewById(R.id.value3);
                        answer=val.getText().toString();
                    }
                    else {
                        third.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#181825")));
                        cardViewLastChecked =null;
                        check=false;
                        answer=null;
                    }
                }
                else {
                    check=true;
                    third.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2132B")));
                    cardViewLastChecked =third;
                    TextView val=third.findViewById(R.id.value3);
                    answer=val.getText().toString();
                }

            }
        });
        forth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check) {
                    cardViewLastChecked.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#181825")));
                    if(forth.getBackgroundTintList().equals(ColorStateList.valueOf(Color.parseColor("#181825")))){
                       forth.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2132B")));
                        cardViewLastChecked =forth;
                        check=true;
                        TextView val=forth.findViewById(R.id.value4);
                        answer=val.getText().toString();
                    }
                    else {
                        forth.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#181825")));
                        cardViewLastChecked =null;
                        check=false;
                        answer=null;
                    }
                }
                else {
                    check=true;
                   forth.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2132B")));
                    cardViewLastChecked =forth;
                    TextView val=forth.findViewById(R.id.value4);
                    answer=val.getText().toString();
                }

            }
        });
        fivth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check) {
                    cardViewLastChecked.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#181825")));
                    if(fivth.getBackgroundTintList().equals(ColorStateList.valueOf(Color.parseColor("#181825")))){
                        fivth.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2132B")));
                        cardViewLastChecked =fivth;
                        check=true;
                        TextView val=fivth.findViewById(R.id.value5);
                        answer=val.getText().toString();
                    }
                    else {
                        fivth.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#181825")));
                        cardViewLastChecked =null;
                        check=false;
                        answer=null;
                    }
                }
                else {
                    check=true;
                    fivth.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2132B")));
                    cardViewLastChecked =fivth;
                    TextView val=fivth.findViewById(R.id.value5);
                    answer=val.getText().toString();
                }

            }
        });
        sixth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check) {
                    cardViewLastChecked.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#181825")));
                    if(sixth.getBackgroundTintList().equals(ColorStateList.valueOf(Color.parseColor("#181825")))){
                        sixth.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2132B")));
                        cardViewLastChecked =sixth;
                        check=true;
                        TextView val=sixth.findViewById(R.id.value6);
                        answer=val.getText().toString();
                    }
                    else {
                        sixth.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#181825")));
                        cardViewLastChecked =null;
                        check=false;
                        answer=null;
                    }
                }
                else {
                    check=true;
                    sixth.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2132B")));
                    cardViewLastChecked =sixth;
                    TextView val=sixth.findViewById(R.id.value6);
                    answer=val.getText().toString();
                }

            }
        });


    }
    // az aktualis aktiv kerdest tolti be az adatbazisbol
    private void  getQuestion() {
        questionsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item: dataSnapshot.getChildren()){
                    String txt = item.child("groupId").getValue().toString();

                    if (txt.equals(groupId)) {
                        if (item.child("active").getValue().toString().equals("true")) {
                            question = item.child("question").getValue().toString();

                        }
                    }


                }
                textViewQuestion.setText(question);
                llProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}