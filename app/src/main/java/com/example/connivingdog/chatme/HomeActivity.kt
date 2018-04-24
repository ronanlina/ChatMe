package com.example.connivingdog.chatme

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var email: String = getEmail()

        try {
            mDatabaseReference.child("users").orderByChild("email").equalTo(email)
            mDatabaseReference.addValueEventListener( object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.forEach{
                        var userAccount: UserAccount = it.getValue(UserAccount::class.java)!!
                        var email: String = userAccount.email
                        var mAdapter = UserListAdapter(mDatabaseReference, this@HomeActivity,email)
                        userMessageList.adapter = mAdapter
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            })
        }
        catch (e: NullPointerException){
            Toast.makeText(this,"NULL",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getEmail(): String{
        var intent: Intent = getIntent()
            return intent.getStringExtra("email")
    }
}
