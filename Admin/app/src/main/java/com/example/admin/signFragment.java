package com.example.admin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;



public class signFragment extends Fragment {
   EditText groupid;
   Button openID;
    public boolean conSession;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference adminReference = database.getReference().child("Groups");

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v;
       v =inflater.inflate(R.layout.fragment_sign, container, false);
        groupid = v.findViewById(R.id.groupId);
        openID=(Button) v.findViewById(R.id.open);
       /* openID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
             //   Intent intent = new Intent(view.getContext(), FragmentShow.class);
                String id =groupid.getText().toString();
                String key = FirebaseDataHelper.Instance.CreateNewGroup(id);
                if (key== "Invalid") {
                    Toast.makeText(getActivity(),"Failed!",Toast.LENGTH_SHORT).show();;

                }
                else {
                    Toast.makeText(getActivity(),key,Toast.LENGTH_SHORT).show();;
                }




            }
        });

        */
       openID.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String id =groupid.getText().toString();
               connectSession(id);



           }
       });

        return v;
    }
    public void connectSession(final String id) {

        conSession = false;

        adminReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                  conSession = false;
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Log.i("FBDB", item.child("groupId").getValue() + "  " + id);
                    if (item.child("groupId").getValue().equals(id)) {

                        conSession = true;
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.fragment_container, new FragmentShow());
                        fr.commit();
                        break;
                    }
                }
                if (conSession == false)
                {
                    groupid.setError("Does not exist.");

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        }); }





}
