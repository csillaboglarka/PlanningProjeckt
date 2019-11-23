package com.example.pokeruser.Adapters;

import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pokeruser.Classes.AnswerItem;
import com.example.pokeruser.Classes.QuestionItem;
import com.example.pokeruser.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private ArrayList<QuestionItem> mQuestionList;
    private static String answer;
    private static boolean check = false;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference answerReference = database.getReference().child("Answers");

    public QuestionAdapter(ArrayList<QuestionItem> questionList) {

        this.mQuestionList = questionList;

    }


    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        QuestionViewHolder qvh = new QuestionViewHolder(v);
        return qvh;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        QuestionItem currentItem = mQuestionList.get(position);
        holder.mQuestion.setText(currentItem.question);
        holder.nameText.setText(currentItem.name);
        holder.groupid.setText(currentItem.groupId);
    }


    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {

        TextView mQuestion;
        Button mAnwer;
         String mName;
         String groupId;
         TextView nameText;
         TextView groupid;


        private QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            mQuestion = itemView.findViewById(R.id.QuestionTextView);
            nameText = itemView.findViewById(R.id.userName);
            groupid=itemView.findViewById(R.id.idgroup);
            mName= nameText.getText().toString();
            groupId=groupid.getText().toString();
            mAnwer = itemView.findViewById(R.id.AnswerButton);
            //itt kezdhetunk el valaszolni ami egy uj dialogot nyit meg
            mAnwer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle(mQuestion.getText().toString());
                        final RelativeLayout rl= new RelativeLayout(v.getContext());
                        final GridLayout gr=new GridLayout(v.getContext());
                        final Button b1 = new Button(v.getContext());
                        final Button b2 = new Button(v.getContext());
                        final Button b3 = new Button(v.getContext());
                        final Button b4 = new Button(v.getContext());
                        final Button b5 = new Button(v.getContext());
                        final Button b6 = new Button(v.getContext());

                        b1.setText("1");
                        b2.setText("5");
                        b3.setText("10");
                        b4.setText("20");
                        b5.setText("50");
                        b6.setText("100");
                        b1.setTextSize(35);
                        b2.setTextSize(35);
                        b3.setTextSize(35);
                        b4.setTextSize(35);
                        b5.setTextSize(35);
                        b6.setTextSize(35);

                        b1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!check) {
                                    b1.setTextColor(Color.GREEN);
                                    answer = b1.getText().toString();
                                    check = true;

                                }
                                else {
                                    b1.setTextColor(Color.BLACK);
                                    check=false;
                                }
                            }
                        });
                        b2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!check ) {
                                    b2.setTextColor(Color.GREEN);
                                    answer = b2.getText().toString();
                                    check = true;

                                }
                                else {
                                    b2.setTextColor(Color.BLACK);
                                    check=false;
                                }

                            }
                        });
                        b3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!check) {
                                    b3.setTextColor(Color.GREEN);
                                    answer = b3.getText().toString();
                                    check = true;

                                }
                                else {
                                    b3.setTextColor(Color.BLACK);
                                    check=false;
                                }

                            }
                        });
                        b4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!check) {
                                    b4.setTextColor(Color.GREEN);
                                    answer = b4.getText().toString();
                                    check = true;

                                }
                                else {
                                    b4.setTextColor(Color.BLACK);
                                    check=false;
                                }

                            }
                        });
                        b5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!check) {
                                    b5.setTextColor(Color.GREEN);
                                    answer = b5.getText().toString();
                                    check = true;

                                }
                                else {
                                    b5.setTextColor(Color.BLACK);
                                    check=false;
                                }

                            }
                        });
                        b6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!check ) {
                                    b6.setTextColor(Color.GREEN);
                                    answer = b6.getText().toString();
                                    check = true;

                                }
                                else {
                                    b6.setTextColor(Color.BLACK);
                                    check=false;
                                }

                            }
                        });
                        rl.setGravity(1);
                        gr.setColumnCount(2);
                        gr.setRowCount(3);
                        gr.addView(b1);
                        gr.addView(b2);
                        gr.addView(b3);
                        gr.addView(b4);
                        gr.addView(b5);
                        gr.addView(b6);
                        rl.addView(gr);
                        builder.setView(rl);

                    builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Lementi a valaszunkat
                                mName= nameText.getText().toString();
                                groupId=groupid.getText().toString();
                                AnswerItem my= new AnswerItem(mName,answer,groupId,mQuestion.getText().toString());
                                                   String newKey = answerReference.push().getKey();
                                                   if(newKey!=null){
                                                   answerReference.child(newKey).setValue(my);
                                                   }
                            }
                        });

                        builder.show();
                    }
                }
            });

        }
    }
}