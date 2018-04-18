package com.example.connivingdog.chatme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity() {

    private var mDatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}
