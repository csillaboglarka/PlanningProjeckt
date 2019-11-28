package com.example.admin.Adapter;


import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.admin.Classes.QuestionItem;
import com.example.admin.Fragments.AnswerResultFragment;
import com.example.admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private ArrayList<QuestionItem> mQuestionList;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference questionsReference = database.getReference().child("Questions");
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;
    private static String groupId;
    private static FragmentManager context;


    public QuestionAdapter(ArrayList<QuestionItem> questionList, String groupId, FragmentManager context) {
        this.mQuestionList = questionList;
        this.groupId=groupId;
        this.context=context;

    }



    public void AddNewItem(QuestionItem newQuestion) {
        if (newQuestion != null) {
            this.mQuestionList.add(newQuestion);
        }
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        QuestionViewHolder qvh = new QuestionViewHolder(v);
        return qvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionViewHolder holder, final int position) {
        final QuestionItem currentItem = mQuestionList.get(position);
        holder.mQuestion.setText(currentItem.question);
        holder.mSwitch.setChecked(mQuestionList.get(position).isActive());
        holder.mSwitch.setTag(new Integer(position));
        if( mQuestionList.get(position).isActive() && holder.mSwitch.isChecked())
        {
            lastChecked = holder.mSwitch;
            lastCheckedPos = position;
        }

        holder.mSwitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckBox cb = (CheckBox)v;
                int clickedPos = ((Integer)cb.getTag()).intValue();

                if(cb.isChecked())
                {
                    if(lastChecked != null)
                    {
                        lastChecked.setChecked(false);
                        mQuestionList.get(lastCheckedPos).setActive(false);
                        final String q =mQuestionList.get(lastCheckedPos).question;
                        questionsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot item : dataSnapshot.getChildren()) {

                                    if (item.child("question").getValue().toString().equals(q)) {
                                        String key = item.getKey();
                                        questionsReference.child(key).child("active").setValue("false");
                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }
                else
                    lastChecked = null;
                mQuestionList.get(clickedPos).setActive(cb.isChecked());
                questionsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot item : dataSnapshot.getChildren()) {
                            if (item.child("question").getValue().toString().equals(mQuestionList.get(position).question)) {
                                String key = item.getKey();
                                if(item.child("active").getValue().toString().equals("false"))
                                    questionsReference.child(key).child("active").setValue("true");
                                else {
                                    questionsReference.child(key).child("active").setValue("false");
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }





    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {

        private TextView mQuestion;
       // public ImageView mDeleteQuestion;
        private CheckBox mSwitch;


        public QuestionViewHolder(@NonNull final View itemView) {
            super(itemView);

            mQuestion = itemView.findViewById(R.id.QuestionTextView);
            mSwitch = itemView.findViewById(R.id.simpleSwitch);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        FragmentTransaction fr = context.beginTransaction();
                        Fragment f = new AnswerResultFragment();
                        fr.addToBackStack(null);
                        fr.replace(R.id.fragment_container,f);
                        Bundle args = new Bundle();
                        args.putString("question",mQuestion.getText().toString());
                        args.putString("groupId", groupId);
                        f.setArguments(args);
                        fr.commit();




                    }

                }
            });

        }


    }
}
        //            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onLongPress(position);
//                        }
//                    }
//
//                    return true;
//                }
//            });

