package com.example.admin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.admin.Database.FirebaseDataHelper;
import com.example.admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class signFragment extends Fragment {
   private EditText groupid;
   private Button openID;
   private Button NewGroup;
   private boolean conSession;
   private FirebaseDatabase database = FirebaseDatabase.getInstance();
   private DatabaseReference adminReference = database.getReference().child("Groups");
   private String id;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v;
       v =inflater.inflate(R.layout.fragment_sign, container, false);
        groupid = v.findViewById(R.id.groupId);
        openID= v.findViewById(R.id.open);
        NewGroup = v.findViewById(R.id.newfragment);

        NewGroup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
             //   Intent intent = new Intent(view.getContext(), FragmentShow.class);
                id =groupid.getText().toString();
                adminReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot item: dataSnapshot.getChildren()){
                            if(item.child("groupId").getValue().equals(id)) {
                                Toast.makeText(getContext(),"Mar letezik ilyen csoport",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String key2 = FirebaseDataHelper.Instance.CreateNewGroup(id);
                                if (key2.equals("Invalid")) {
                                    Toast.makeText(getActivity(),"Failed!",Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Toast.makeText(getActivity(),key2,Toast.LENGTH_SHORT).show();
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


       openID.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String id =groupid.getText().toString();
               connectSession(id);
           }
       });

        return v;
    }
    private void connectSession(final String id) {

        conSession = false;

        adminReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                  conSession = false;
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    if (item.child("groupId").getValue().equals(id)) {
                        conSession = true;
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        Fragment f = new FragmentShow();
                        fr.addToBackStack(null);
                        fr.replace(R.id.fragment_container,f);
                        Bundle args = new Bundle();
                        args.putString("groupId",groupid.getText().toString());
                        f.setArguments(args);
                        fr.commit();
                        break;
                    }
                }
                if (!conSession )
                {
                    groupid.setError("Does not exist.");

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        }); }





}
