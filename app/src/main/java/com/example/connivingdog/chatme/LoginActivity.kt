package com.example.connivingdog.chatme

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener{view ->
            //TODO FirebaseAuth sign in method
            attemptLogIn();
        }

        signupButton.setOnClickListener {view ->
            var intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }

    //basic checking of valid email format
    private fun isEmailValid(): Boolean{
        if(emailLogText.text.contains("@")){
            return true
        }
        return false
    }

    //form validation
    private fun attemptLogIn(){
         var focusView: View? = null
         var cancel: Boolean = false

        if(!isEmailValid()){
            emailLogText?.error = "please enter email"
            cancel = true
            focusView = emailLogText
        }

        if(cancel){
            focusView?.requestFocus()
        }
        else{
            logIn()
        }
    }

    //signing in the user
    private fun logIn(){
        Toast.makeText(this,"Signing in...",Toast.LENGTH_LONG).show()

        var email: String = emailLogText.text.toString()
        var password: String = passwordLogText.text.toString()

        fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
            if(task.isSuccessful){
                //TODO start intent home activity
                Toast.makeText(this,"Welcome!",Toast.LENGTH_LONG).show()
                var intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("email",email)
                startActivity(intent)
            }else{
                //TODO prompt
                Toast.makeText(this,"Login error: please check your connection.",Toast.LENGTH_LONG).show()
            }
        })
    }
}
