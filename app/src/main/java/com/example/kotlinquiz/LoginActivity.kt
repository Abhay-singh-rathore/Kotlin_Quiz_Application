package com.example.kotlinquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinquiz.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var loginBinding: ActivityLoginBinding

    val auth =FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        enableEdgeToEdge()
        setContentView(view)
        loginBinding.buttonSignIn.setOnClickListener{

            val userEmail = loginBinding.edittextLoginEmail.text.toString()
            val userPassword = loginBinding.edittextloginpassword.text.toString()

            signInUser(userEmail,userPassword)

        }
        loginBinding.buttonGoogleSignIn.setOnClickListener {  }

        loginBinding.textViewSignUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        loginBinding.textviewForgetPassword.setOnClickListener{

        }

    }

    fun signInUser(userEmail : String,userPassword : String){

        auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener { task->

            if (task.isSuccessful){
                Toast.makeText(applicationContext,"Welcome  to Quiz ",Toast.LENGTH_SHORT).show()
                val intent =Intent(this@LoginActivity,MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {

                Toast.makeText(applicationContext,task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()

            }
        }

    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser

        if(user != null ){
            Toast.makeText(applicationContext,"Welcome  to Quiz ",Toast.LENGTH_SHORT).show()
            val intent =Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}