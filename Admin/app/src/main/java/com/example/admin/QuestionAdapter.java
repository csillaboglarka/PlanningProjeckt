package com.example.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Interfaces.OnItemClickListener;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    public ArrayList<QuestionItem> mQuestionList;



    private OnItemClickListener mListener;


    public QuestionAdapter(ArrayList<QuestionItem> questionList) {
        this.mQuestionList = questionList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
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
        QuestionViewHolder qvh = new QuestionViewHolder(v, mListener);
        return qvh;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        QuestionItem currentItem = mQuestionList.get(position);
        holder.mQuestion.setText(currentItem.question);
    }



    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {

        public TextView mQuestion;
        public ImageView mDeleteQuestion;


        public QuestionViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            mQuestion = itemView.findViewById(R.id.QuestionTextView);
            mDeleteQuestion = itemView.findViewById(R.id.DeleteItemImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onLongPress(position);
                        }
                    }

                    return true;
                }
            });

            mDeleteQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

        }
    }
}
