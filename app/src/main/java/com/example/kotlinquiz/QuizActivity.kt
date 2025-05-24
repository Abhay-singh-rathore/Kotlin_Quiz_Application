package com.example.kotlinquiz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinquiz.databinding.ActivityQuizBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

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
      var questionCount=0
    var questionNumber=0

    var userAnswer =""
    var userCorrect=0
    var userWrong = 0

    lateinit var  timer: CountDownTimer
    private val totalTime = 90000L
    var timerContinue =false
    var leftTime = totalTime


    val auth =FirebaseAuth.getInstance()
    var user =auth.currentUser
    val scoreRef =database.reference

    val questions = HashSet<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizBinding=ActivityQuizBinding.inflate(layoutInflater)
        val view = quizBinding.root
        enableEdgeToEdge()
        setContentView(view)

        do {
            val number = Random.nextInt(1,11)
            Log.d("number",number.toString())
            questions.add(number)
        } while (questions.size < 5)

        Log.d("numberOfQuestions",questions.toString())
        gameLogic()



        quizBinding.buttonNext.setOnClickListener {


            resetTimer()


            gameLogic()


        }

        quizBinding.buttonFinish.setOnClickListener {
            sendScore()

        }

        quizBinding.textViewA.setOnClickListener {
            pauseTimer()

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
            pauseTimer()
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

            pauseTimer()
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
                pauseTimer()
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

                if(questionNumber < questions.size){

                    question=snapshot.child(questions.elementAt(questionNumber).toString()).child("q").value.toString()
                    answerA=snapshot.child(questions.elementAt(questionNumber).toString()).child("a").value.toString()
                    answerB=snapshot.child(questions.elementAt(questionNumber).toString()).child("b").value.toString()
                    answerC=snapshot.child(questions.elementAt(questionNumber).toString()).child("c").value.toString()
                    answerD=snapshot.child(questions.elementAt(questionNumber).toString()).child("d").value.toString()
                    correctAnswer=snapshot.child(questions.elementAt(questionNumber).toString()).child("answer").value.toString().trim().lowercase()


                    quizBinding.textViewQuestion.text= question
                    quizBinding.textViewA.text =answerA
                    quizBinding.textViewB.text=answerB
                    quizBinding.textViewC.text=answerC
                    quizBinding.textViewD.text=answerD

                    quizBinding.progressBarQuiz.visibility= View.INVISIBLE
                    quizBinding.linearlayoutInfo.visibility=View.VISIBLE
                    quizBinding.linearLayoutQuestion.visibility=View.VISIBLE
                    quizBinding.linearLayoutButton.visibility=View.VISIBLE


                    startTimer()



                } else{
                    val dialogMessage = AlertDialog.Builder(this@QuizActivity)
                    dialogMessage.setTitle(" QUIZ ")
                    dialogMessage.setMessage("Congratulation!!! \n You have answered all question . Do you want to see the result?")
                dialogMessage.setCancelable(false)
                dialogMessage.setPositiveButton("see Result"){dialogWindow,position ->
                    sendScore()
                }
                    dialogMessage.setNegativeButton("Answer Again"){dialogwindow,position ->
                        val intent=Intent(this@QuizActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    dialogMessage.create().show()
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

    private fun startTimer(){
        timer= object : CountDownTimer(leftTime,1000){
            override fun onTick(millisUntilFinish : Long) {
                leftTime = millisUntilFinish
                updateCountDownText()
            }

            override fun onFinish() {

               disableClicableOfOptions()

                resetTimer()
                updateCountDownText()


                quizBinding.textViewQuestion.text = " Sorry, Time is Up!"
                timerContinue = false
            }

        }.start()

        timerContinue = true
    }

    fun updateCountDownText(){

        val remainingTime : Int = (leftTime/1000).toInt()
        quizBinding.textViewtime.text =remainingTime.toString()

    }

    fun pauseTimer(){

        timer.cancel()
        timerContinue = false

    }

    fun resetTimer(){
     pauseTimer()
        leftTime = totalTime
        updateCountDownText()
    }

    fun sendScore(){
        user?.let {
            val userUID =it.uid
            scoreRef.child("scores").child(userUID).child("corrrect").setValue(userCorrect)
            scoreRef.child("scores").child(userUID).child("wrong").setValue(userWrong).addOnSuccessListener {
                Toast.makeText(applicationContext,"Scorces data sent succesfully to database",Toast.LENGTH_SHORT).show()

                val intent = Intent(this@QuizActivity,ResultActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}