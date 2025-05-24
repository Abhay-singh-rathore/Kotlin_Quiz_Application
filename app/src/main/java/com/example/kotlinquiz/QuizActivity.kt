package com.example.kotlinquiz

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinquiz.databinding.ActivityQuizBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class QuizActivity : AppCompatActivity() {
    lateinit var quizBinding : ActivityQuizBinding

    val database = FirebaseDatabase.getInstance()
    val databaseReference=database.reference.child("questions")

      var question=""
      var answerA=""
      var answerB=""
       var answerC=""
       var answerD=""
       var correctAnswer=""
      var questionCount= 1
    var questionNumber=1

    var userAnswer =""
    var userCorrect=0
    var userWrong = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizBinding=ActivityQuizBinding.inflate(layoutInflater)
        val view = quizBinding.root
        enableEdgeToEdge()
        setContentView(view)

        gameLogic()

        quizBinding.buttonFinish.setOnClickListener {


        }

        quizBinding.buttonNext.setOnClickListener {
            gameLogic()

        }

        quizBinding.textViewA.setOnClickListener {

            userAnswer= "a"

            if (correctAnswer == userAnswer){
                quizBinding.textViewA.setBackgroundColor(Color.GREEN)
                userCorrect++
                quizBinding.textViewCorrect.text=userCorrect.toString()

            } else{
                quizBinding.textViewA.setBackgroundColor(Color.RED)
                userWrong++
                quizBinding.textViewWrong.text =userWrong.toString()
                findAnswer()
            }
            disableClicableOfOptions()
        }

        quizBinding.textViewB.setOnClickListener {
            userAnswer="b"

            if (correctAnswer == userAnswer){
                quizBinding.textViewB.setBackgroundColor(Color.GREEN)
                userCorrect++
                quizBinding.textViewCorrect.text=userCorrect.toString()

            } else{
                quizBinding.textViewB.setBackgroundColor(Color.RED)
                userWrong++
                quizBinding.textViewWrong.text =userWrong.toString()
                findAnswer()
            }

            disableClicableOfOptions()
        }

        quizBinding.textViewC.setOnClickListener {
            userAnswer="c"

            if (correctAnswer == userAnswer){
                quizBinding.textViewC.setBackgroundColor(Color.GREEN)
                userCorrect++
                quizBinding.textViewCorrect.text=userCorrect.toString()

            } else{
                quizBinding.textViewC.setBackgroundColor(Color.RED)
                userWrong++
                quizBinding.textViewWrong.text =userWrong.toString()
                findAnswer()
            }
            disableClicableOfOptions()

        }

        quizBinding.textViewD.setOnClickListener {

            userAnswer="d"

            if (correctAnswer == userAnswer){
                quizBinding.textViewD.setBackgroundColor(Color.GREEN)
                userCorrect++
                quizBinding.textViewCorrect.text=userCorrect.toString()

            } else{
                quizBinding.textViewD.setBackgroundColor(Color.RED)
                userWrong++
                quizBinding.textViewWrong.text =userWrong.toString()
                findAnswer()
            }
            disableClicableOfOptions()
        }


    }

    private fun gameLogic(){

        restoreOptoins()


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                questionCount=snapshot.childrenCount.toInt()

                if(questionNumber <= questionCount){

                    question=snapshot.child(questionNumber.toString()).child("q").value.toString()
                    answerA=snapshot.child(questionNumber.toString()).child("a").value.toString()
                    answerB=snapshot.child(questionNumber.toString()).child("b").value.toString()
                    answerC=snapshot.child(questionNumber.toString()).child("c").value.toString()
                    answerD=snapshot.child(questionNumber.toString()).child("d").value.toString()
                    correctAnswer=snapshot.child(questionNumber.toString()).child("answer").value.toString().trim().lowercase()


                    quizBinding.textViewQuestion.text= question
                    quizBinding.textViewA.text =answerA
                    quizBinding.textViewB.text=answerB
                    quizBinding.textViewC.text=answerC
                    quizBinding.textViewD.text=answerD

                    quizBinding.progressBarQuiz.visibility= View.INVISIBLE
                    quizBinding.linearlayoutInfo.visibility=View.VISIBLE
                    quizBinding.linearLayoutQuestion.visibility=View.VISIBLE
                    quizBinding.linearLayoutButton.visibility=View.VISIBLE


                } else{
                    Toast.makeText(applicationContext,"You answered all the questions",Toast.LENGTH_SHORT).show()
                }


                quizBinding.buttonNext.setOnClickListener {
                    questionNumber++
                    gameLogic()
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.message,Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun findAnswer(){
        when(correctAnswer){
            "a" -> quizBinding.textViewA.setBackgroundColor(Color.GREEN)
            "b" -> quizBinding.textViewB.setBackgroundColor(Color.GREEN)
            "c" -> quizBinding.textViewC.setBackgroundColor(Color.GREEN)
            "d" -> quizBinding.textViewD.setBackgroundColor(Color.GREEN)



        }
    }

    fun disableClicableOfOptions(){
        quizBinding.textViewA.isClickable = false
        quizBinding.textViewB.isClickable = false
        quizBinding.textViewC.isClickable = false
        quizBinding.textViewD.isClickable = false
    }

    fun restoreOptoins(){
        quizBinding.textViewA.setBackgroundColor(Color.WHITE)
        quizBinding.textViewB.setBackgroundColor(Color.WHITE)
        quizBinding.textViewC.setBackgroundColor(Color.WHITE)
        quizBinding.textViewD.setBackgroundColor(Color.WHITE)

        quizBinding.textViewA.isClickable=true
        quizBinding.textViewB.isClickable=true
        quizBinding.textViewC.isClickable=true
        quizBinding.textViewD.isClickable=true
    }
}