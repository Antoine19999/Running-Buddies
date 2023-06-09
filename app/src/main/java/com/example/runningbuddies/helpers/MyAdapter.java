package com.example.runningbuddies.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningbuddies.R;
import com.example.runningbuddies.interfaces.User;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context mContext;

    ArrayList<User> mUsers;

    public MyAdapter(Context context, ArrayList<User> users) {
        mContext = context;
        mUsers = users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User user = mUsers.get(position);
        String userCompleteName = user.getFirstName() + " " + user.getLastName();
        holder.userName.setText(userCompleteName);
        holder.age.setText(String.valueOf(user.getUserAge()));
        holder.minSpeed.setText(user.getUserMinSpeed());
        holder.maxSpeed.setText(user.getUserMaxSpeed());
        holder.timePreference.setText(user.getUserTimePreference());

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userName, age, minSpeed, maxSpeed, timePreference;
        Button sendFriendRequestButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            age = itemView.findViewById(R.id.tvAge);
            userName = itemView.findViewById(R.id.tvUserName);
            minSpeed = itemView.findViewById(R.id.tvMinSpeed);
            maxSpeed = itemView.findViewById(R.id.tvMaxSpeed);
            timePreference = itemView.findViewById(R.id.tvTimePreference);
            sendFriendRequestButton = itemView.findViewById(R.id.button_send_request);
        }
    }
}
