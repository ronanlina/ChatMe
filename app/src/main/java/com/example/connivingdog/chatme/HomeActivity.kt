package com.example.connivingdog.chatme

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Adapter
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var mDataList = arrayListOf<UserAccount>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var account: UserAccount
        var mAdapter: UserAccountAdapter

        var email: String = getEmail()
        mDatabaseReference.child("users").addValueEventListener( object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot!=null){
                    account = dataSnapshot.getValue(UserAccount::class.java)!!
                    //Log.d("DSNS", account!!.email)
                    if (!account.email.equals(email)) { // the '!=' equivalent query
                        mDataList.add(account)
                    }
                }
                else{
                    Toast.makeText(this@HomeActivity,"null",Toast.LENGTH_SHORT)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        mAdapter = UserAccountAdapter(this@HomeActivity,mDataList)
        userMessageList.adapter = mAdapter
    }

    private fun getEmail(): String{
        var intent: Intent = intent
            return intent.getStringExtra("email")
    }

    override fun onStart() {
        super.onStart()
        /*var email: String = getEmail()

        mAdapter = UserAccountAdapter(this,)
        userMessageList.adapter = mAdapter*/
    }

    override fun onStop() {
        super.onStop()
        //mAdapter!!.cleanup()
    }
}
