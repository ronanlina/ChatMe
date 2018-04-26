package com.example.connivingdog.chatme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserAccountAdapter  extends  RecyclerView.Adapter<UserAccountAdapter.ViewHolder>{

    private Context context;
    private ArrayList<UserAccount> mAccounts;

    public UserAccountAdapter(Context context, ArrayList<UserAccount> accounts) {
        this.context = context;
        mAccounts = accounts;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserAccount accs = mAccounts.get(position);

        holder.emailText.setText(accs.getEmail());
        holder.userText.setText(accs.getUsername());
    }

    @Override
    public int getItemCount() {
        return mAccounts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView userText;
        TextView emailText;

        public ViewHolder(View itemView) {
            super(itemView);

            userText = itemView.findViewById(R.id.userText);
            emailText = itemView.findViewById(R.id.emailText);
        }
    }

}
