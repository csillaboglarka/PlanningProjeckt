package com.example.pokeruser.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.pokeruser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signFragment extends Fragment {
    EditText groupid;
    Button openID;
    EditText uName;
    public boolean conSession;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference adminReference = database.getReference().child("Groups");




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v =inflater.inflate(R.layout.fragment_sign, container, false);
        groupid = v.findViewById(R.id.groupId);
        uName =v.findViewById(R.id.Name);

        openID= v.findViewById(R.id.open);
        openID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id =groupid.getText().toString();
                String myName = uName.getText().toString();
                connectSession(id,myName);



            }
        });

        return v;
    }
    public void connectSession(final String id,final String myName) {

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
                        Fragment f = new FragmentShow();
                        fr.replace(R.id.fragment_container,f);
                        Bundle args = new Bundle();
                        args.putString("groupId",id);
                        args.putString("Name",myName);
                        f.setArguments(args);

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
