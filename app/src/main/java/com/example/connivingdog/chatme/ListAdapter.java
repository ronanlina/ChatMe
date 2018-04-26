package com.example.connivingdog.chatme;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private DatabaseReference mDatabaseReference;
    private Activity mActivity;
    private ArrayList<DataSnapshot> mSnapshotList;
    private String email;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if(!dataSnapshot.getValue(UserAccount.class).getEmail().equals(email)) { // the '!=' equivalent query
                mSnapshotList.add(dataSnapshot);
                notifyDataSetChanged();
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public ListAdapter(DatabaseReference databaseReference, Activity activity, String currentUserEmail) {
        mActivity = activity;
        email = currentUserEmail;
        mDatabaseReference = databaseReference.child("users");
        mDatabaseReference.addChildEventListener(mListener);
        mSnapshotList = new ArrayList<>();
    }

    static class ViewHolder{
        TextView userText;
        TextView emailText;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public UserAccount getItem(int i) {
        DataSnapshot snapshot = mSnapshotList.get(i);
        return snapshot.getValue(UserAccount.class);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.user_list, viewGroup, false);

            final ViewHolder holder = new ViewHolder();
            holder.userText = (TextView) view.findViewById(R.id.userText);
            holder.emailText = (TextView) view.findViewById(R.id.emailText);
            holder.params = (LinearLayout.LayoutParams) holder.userText.getLayoutParams();
            view.setTag(holder);
        }

        final UserAccount account = getItem(i);
        final ViewHolder holder = (ViewHolder) view.getTag();

            String username = account.getUsername();
            String email = account.getEmail();

            holder.userText.setText(username);
            holder.emailText.setText(email);

        return view;
    }

    public void cleanup(){
        mDatabaseReference.removeEventListener(mListener);
    }
}
