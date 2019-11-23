package com.example.pokeruser.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.example.pokeruser.Classes.QuestionItem;
import com.example.pokeruser.R;

import java.util.ArrayList;






public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    public ArrayList<QuestionItem> mQuestionList;




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
        QuestionViewHolder qvh = new QuestionViewHolder(v);
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


        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            mQuestion = itemView.findViewById(R.id.QuestionTextView);








        }
    }
}
