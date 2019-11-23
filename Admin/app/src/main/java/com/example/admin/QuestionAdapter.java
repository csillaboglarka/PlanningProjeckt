package com.example.admin;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Interfaces.OnItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    public ArrayList<QuestionItem> mQuestionList;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference adminReference = database.getReference().child("Groups");
    static DatabaseReference questionsReference = database.getReference().child("Questions");

    public  int position;

  //  private onItemClickListener mListener;


    public QuestionAdapter(ArrayList<QuestionItem> questionList) {
        this.mQuestionList = questionList;
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
        QuestionViewHolder qvh = new QuestionViewHolder(v,position);
        return qvh;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        QuestionItem currentItem = mQuestionList.get(position);
        holder.mQuestion.setText(currentItem.question);

        if(currentItem.active == true) {

            holder.mSwitch.setChecked(true);
        }

    }





    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {

        public TextView mQuestion;
       // public ImageView mDeleteQuestion;
        public Switch mSwitch;


        public QuestionViewHolder(@NonNull View itemView, int postition) {
            super(itemView);

            mQuestion = itemView.findViewById(R.id.QuestionTextView);
            mSwitch = itemView.findViewById(R.id.simpleSwitch);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });
           mSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        final String myQuestion = mQuestion.getText().toString();
                        questionsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot item : dataSnapshot.getChildren()) {
                                    String db = item.child("question").getValue().toString();

                                    if (db.equals(myQuestion)) {
                                        String key = item.getKey();
                                        if (item.child("active").getValue().toString().equals("true")) {
                                            questionsReference.child(key).child("active").setValue("false");
                                        } else {
                                            questionsReference.child(key).child("active").setValue("true");
                                        }

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


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

