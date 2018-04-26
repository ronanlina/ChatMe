package com.example.connivingdog.chatme

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.common.config.GservicesValue.init

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query

import java.util.ArrayList

class UserListAdapter(databaseReference: DatabaseReference, private val mActivity: Activity, currentUserEmail: String) : BaseAdapter() {

    private val mDatabaseReference: DatabaseReference
    private var mSnapshotList: ArrayList<DataSnapshot>
    private var mListener: ChildEventListener
    //private val query: Query


    init {
        mDatabaseReference = databaseReference.child("users")
        //query = mDatabaseReference.orderByChild("username").equalTo(currentUserEmail)
        //query.addChildEventListener(mListener)
        mSnapshotList = ArrayList()
        mListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String) {
                mSnapshotList.add(dataSnapshot)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        mDatabaseReference.addChildEventListener(mListener)
    }

    internal class ViewHolder {
        var userText: TextView? = null
        var emailText: TextView? = null
        var params: LinearLayout.LayoutParams? = null
    }

    override fun getCount(): Int {
        return mSnapshotList.size
    }

    override fun getItem(i: Int): UserAccount? {
        val snapshot = mSnapshotList[i]

        return snapshot.getValue(UserAccount::class.java)
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view

        if (view == null) {
            val inflater = mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.user_list, viewGroup, false)

            val holder = ViewHolder()
            holder.userText = view!!.findViewById<View>(R.id.userText) as TextView
            holder.emailText = view.findViewById<View>(R.id.emailText) as TextView
            holder.params = holder.userText!!.layoutParams as LinearLayout.LayoutParams
            view.tag = holder
        }

        val account = getItem(i)
        val holder = view.tag as ViewHolder

        val username = account!!.username
        val email = account.email

        holder.userText!!.text = username
        holder.emailText!!.text = email
        return view
    }

    public fun cleanup(){
        mDatabaseReference.removeEventListener(mListener)
    }
}
