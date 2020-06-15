package com.example.foodrecipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestHolder> {
    //Declaring variables
    private Context mContext;
    private List<Request> userRequests;

    //Initializing variables
    public RequestAdapter(Context context, List<Request> requests){
        this.mContext = context;
        this.userRequests = requests;
    }

    //setting values to layout
    @NonNull
    @Override
    public RequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.requestitems,parent,false);
        return new RequestHolder(v);
    }

    //Binding all values to layout views
    @Override
    public void onBindViewHolder(@NonNull RequestHolder holder, int position) {
        final Request currentRequest = userRequests.get(position);
        holder.recipeName.setText(currentRequest.getrName());
        holder.originName.setText(currentRequest.getOrigin());
    }

    //Total number of request
    @Override
    public int getItemCount() {
        return userRequests.size();
    }


    public class RequestHolder extends RecyclerView.ViewHolder{
        //Declaration of variables
        public TextView recipeName, originName;

        public RequestHolder(@NonNull View itemView) {
            super(itemView);
            //Initializing variables
            recipeName = itemView.findViewById(R.id.RecipeName);
            originName =itemView.findViewById(R.id.OriginName);

        }
    }
}
