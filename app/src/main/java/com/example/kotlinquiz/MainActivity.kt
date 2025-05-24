package com.example.kotlinquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinquiz.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding =ActivityMainBinding.inflate(layoutInflater)
        val view =mainBinding.root
        enableEdgeToEdge()
        setContentView(view)

        mainBinding.Buttonstartquiz.setOnClickListener {
            val intent = Intent(this,QuizActivity::class.java)
            startActivity(intent)
        }

        mainBinding.buttonSignOut.setOnClickListener {

            //email and password
            FirebaseAuth.getInstance().signOut()
            //Google  account

            val gso =GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

                .requestEmail().build()

            val GoogleSignInClient =GoogleSignIn.getClient(this,gso)
            GoogleSignInClient.signOut().addOnCompleteListener { task ->
                if(task.isSuccessful){
                          Toast.makeText(applicationContext,"Sign Out is Succesful",Toast.LENGTH_LONG).show()
                } else{

                    Toast.makeText(applicationContext,"Sign Out is Succesful",Toast.LENGTH_SHORT).show()


                }
            }

            val intent =Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}