package com.example.connivingdog.chatme

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {

    private var mDatabaseReference = FirebaseDatabase.getInstance().reference
    private var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        signupAccountButton.setOnClickListener {
            attemptCreateUser()
        }
    }

    private fun attemptCreateUser(){
        var focusView: View? = null
        var cancel: Boolean = false

        if(!isEmailValid()){
            aEmailText?.error = "invalid email format"
            cancel = true
            focusView = aEmailText
        }

        if(!isPasswordValid()){
            aPasswordText?.error = "password should countain 6 or more characters"
            cancel = true
            focusView = aPasswordText
        }

        if(!isUsernameValid()){
            aUsernameText?.error = "please enter a valid username"
            cancel = true
            focusView = aUsernameText
        }

        if(cancel){
            focusView?.requestFocus()
        }
        else{
            logIn()
        }

    }

    private fun logIn(){
        var username: String = aUsernameText.text.toString()
        var email: String = aEmailText.text.toString()
        var password: String = aPasswordText.text.toString()

        fbAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                //Registration OK
                var user = UserAccount(username,email,password)
                mDatabaseReference.child("users").push().setValue(user)
                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_LONG).show()
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Registration Error: please check your connection.", Toast.LENGTH_LONG).show()
            }
        }
    }

    //validation methods
    private fun isEmailValid(): Boolean{
        if(aEmailText.text.contains("@")){
            return true
        }
        return false
    }

    private fun isPasswordValid(): Boolean{
        if(aPasswordText.length() > 6){
            return true
        }
        return false
    }

    private fun isUsernameValid(): Boolean{
        if(aUsernameText.text.toString() != ""){
            return true
        }
        return false
    }
}
