package com.example.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentTransaction;

import com.example.admin.Interfaces.AddQuestionDialogListener;


import java.util.Arrays;

public class AddNewQuestionDialog extends DialogFragment {

    public AddNewQuestionDialog() {

        // Empty constructor required for DialogFragment

    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.question_dialog, container);

    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Get field from view


        EditText editTextQuestion = view.findViewById(R.id.QuestionEditText);

        // Fetch arguments from bundle and set title


        // Show soft keyboard automatically and request focus to field




    }

    }






