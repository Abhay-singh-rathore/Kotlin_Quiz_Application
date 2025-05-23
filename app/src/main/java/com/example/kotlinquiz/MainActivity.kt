package com.example.kotlinquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinquiz.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding =ActivityMainBinding.inflate(layoutInflater)
        val view =mainBinding.root
        enableEdgeToEdge()
        setContentView(view)

        mainBinding.buttonSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent =Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}