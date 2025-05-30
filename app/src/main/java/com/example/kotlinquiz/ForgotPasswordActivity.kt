package com.example.kotlinquiz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinquiz.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var forgotBinding: ActivityForgotPasswordBinding

    val auth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgotBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
         val view = forgotBinding.root
        enableEdgeToEdge()
        setContentView(view)

        forgotBinding.buttonReset.setOnClickListener {

            val userEmail =forgotBinding.edittextForgotEmail.text.toString()
            auth.sendPasswordResetEmail(userEmail).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(applicationContext," We Sent a Password Reset link to email",Toast.LENGTH_SHORT).show()
                    finish()

                } else{

                    Toast.makeText(applicationContext,task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()

                }
            }
        }

    }
}