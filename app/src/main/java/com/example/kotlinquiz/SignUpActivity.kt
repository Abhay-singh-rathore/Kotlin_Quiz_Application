package com.example.kotlinquiz

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinquiz.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var signUpBinding: ActivitySignUpBinding

    val auth : FirebaseAuth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        val view =signUpBinding.root
        enableEdgeToEdge()
        setContentView(view)


        signUpBinding.buttonSignUp.setOnClickListener {
            val email = signUpBinding.edittextSignupEmail.text.toString()
            val password = signUpBinding.edittextSignUppassword.text.toString()

            signUpWithFirebase(email,password)

        }

    }

    fun signUpWithFirebase(email : String, password :String){
        signUpBinding.progressBarSignUp.visibility = View.VISIBLE
        signUpBinding.buttonSignUp.isClickable = false

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(applicationContext,"Your account has been created",Toast.LENGTH_SHORT).show()
                finish()
                signUpBinding.progressBarSignUp.visibility =View.INVISIBLE
                signUpBinding.buttonSignUp.isClickable = true
            } else{

                Toast.makeText(applicationContext,task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()

            }

            }


    }

}