package com.example.pokeruser.Adapters;

import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pokeruser.Classes.AnswerItem;
import com.example.pokeruser.Classes.QuestionItem;
import com.example.pokeruser.Fragments.FragmentShow;

import com.example.pokeruser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;






public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

    public ArrayList<AnswerItem> mAnswerList;



    public AnswerAdapter(ArrayList<AnswerItem> answerList) {
        this.mAnswerList=answerList;


    }



    public void AddNewItem(AnswerItem newAnswer) {
        if (newAnswer != null) {
            this.mAnswerList.add(newAnswer);
        }
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.anwerresult_item, parent, false);
        AnswerViewHolder qvh = new AnswerViewHolder(v);
        return qvh;
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        AnswerItem currentItem = mAnswerList.get(position);
        holder.mQuestion.setText(currentItem.question);
        holder.nametext.setText(currentItem.name);
        holder.value.setText(currentItem.answer);

    }



    @Override
    public int getItemCount() {
        return mAnswerList.size();
    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {

        public TextView mQuestion;
        public TextView nametext;
        public Button value;


        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);

            mQuestion = itemView.findViewById(R.id.QuestionTextView);
            nametext = itemView.findViewById(R.id.myNames);
            value=itemView.findViewById(R.id.AnswerButton);




        }
    }
}