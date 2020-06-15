package com.example.foodrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestList extends AppCompatActivity {
    //Declaration of variables
    private RecyclerView requestRecycler;
    private RequestAdapter cAdapter;
    private DatabaseReference nodePath;
    private List<Request> uRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        //Initialization of variables
        requestRecycler = findViewById(R.id.requestRecycler);
        requestRecycler.setHasFixedSize(true);
        requestRecycler.setLayoutManager(new LinearLayoutManager(this));

        uRequest = new ArrayList<>();
        nodePath = FirebaseDatabase.getInstance().getReference("requests");//reference to node in realtime database

        //Values gotten through the realtime database
        nodePath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot requestSnapshot: dataSnapshot.getChildren()){
                    Request list = requestSnapshot.getValue(Request.class);
                    uRequest.add(list);
                }
                cAdapter = new RequestAdapter(RequestList.this,uRequest);
                requestRecycler.setAdapter(cAdapter);//calling the adapter and setting in recycler view
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RequestList.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
