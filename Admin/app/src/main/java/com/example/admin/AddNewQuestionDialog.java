package com.example.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.question_dialog, null);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());


        alertDialogBuilder.setMessage("Are you sure?");

        alertDialogBuilder.setPositiveButton("ADD",  new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                // on success

            }

        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                if (dialog != null ) {

                    dialog.dismiss();

                }

            }



        });



        return alertDialogBuilder.create();

    }




}

