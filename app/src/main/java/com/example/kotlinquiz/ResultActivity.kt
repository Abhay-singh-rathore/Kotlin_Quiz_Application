package com.example.kotlinquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinquiz.databinding.ActivityResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ResultActivity : AppCompatActivity() {

    lateinit var resultBinding : ActivityResultBinding

    val database =FirebaseDatabase.getInstance()
    val databaseReference =database.reference.child("scores")

    val auth =FirebaseAuth.getInstance()
    val user = auth.currentUser

    var userCorrect =""
    var userWrong=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resultBinding = ActivityResultBinding.inflate(layoutInflater)
        val view =resultBinding.root
        enableEdgeToEdge()
        setContentView(view)


        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user?.let {
                    val userUID =it.uid
                    userCorrect = snapshot.child(userUID).child("corrrect").value.toString().trim().lowercase()
                    userWrong = snapshot.child(userUID).child("wrong").value.toString().trim().lowercase()
                    resultBinding.textViewScoreCorrect.text = userCorrect.toString().trim().lowercase()
                    resultBinding.textViewScoreWrong.text = userWrong.toString().trim().lowercase()


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
          resultBinding.buttonPlayAgain.setOnClickListener {
              val intent =Intent(this,MainActivity::class.java)
              startActivity(intent)
              finish()
          }

        resultBinding.buttonExit.setOnClickListener {

            finish()
        }
    }
}