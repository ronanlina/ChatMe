package com.example.connivingdog.chatme

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Adapter
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var mAdapter: ListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

    }

    private fun getEmail(): String{
        var intent: Intent = intent
            return intent.getStringExtra("email")
    }

    override fun onStart() {
        super.onStart()
        var email: String = getEmail()

        mAdapter = ListAdapter(mDatabaseReference,this@HomeActivity,email)
        userMessageList.adapter = mAdapter
    }

    override fun onStop() {
        super.onStop()
        mAdapter!!.cleanup()
    }
}
