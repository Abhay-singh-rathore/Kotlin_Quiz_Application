package com.example.kotlinquiz

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinquiz.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {
    lateinit var quizBinding : ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizBinding=ActivityQuizBinding.inflate(layoutInflater)
        val view = quizBinding.root
        enableEdgeToEdge()
        setContentView(view)

        quizBinding.buttonFinish.setOnClickListener {

        }

        quizBinding.buttonNext.setOnClickListener {

        }

        quizBinding.textViewA.setOnClickListener {  }

        quizBinding.textViewB.setOnClickListener {  }

        quizBinding.textViewC.setOnClickListener {  }

        quizBinding.textViewD.setOnClickListener {  }
    }
}