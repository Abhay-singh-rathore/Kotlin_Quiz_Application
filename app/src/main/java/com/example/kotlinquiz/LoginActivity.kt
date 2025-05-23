package com.example.kotlinquiz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinquiz.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    lateinit var loginBinding: ActivityLoginBinding

    val auth =FirebaseAuth.getInstance()

    lateinit var googleSignInClient: GoogleSignInClient

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        enableEdgeToEdge()
        setContentView(view)

        val TextOfGoogleButton = loginBinding.buttonGoogleSignIn.getChildAt(0) as TextView

        TextOfGoogleButton.text="Continue With Google"
        TextOfGoogleButton.setTextColor(Color.BLACK)
        TextOfGoogleButton.textSize =18F

        //register

        registerActivityForGoogleSignIn()

        loginBinding.buttonSignIn.setOnClickListener{

            val userEmail = loginBinding.edittextLoginEmail.text.toString()
            val userPassword = loginBinding.edittextloginpassword.text.toString()

            signInUser(userEmail,userPassword)

        }
        loginBinding.buttonGoogleSignIn.setOnClickListener {

       signInGoogle()
        }

        loginBinding.textViewSignUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        loginBinding.textviewForgetPassword.setOnClickListener{

            val intent=Intent(this,ForgotPasswordActivity::class.java)
            startActivity(intent)

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

    private fun signInGoogle(){
      var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
          .requestIdToken("329025869955-ic7l8lgj2jp5b7lsubq6501kcgaau93k.apps.googleusercontent.com")
          .requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)

        signIn()
    }

    private fun signIn(){
        val signInIntent : Intent = googleSignInClient.signInIntent
       activityResultLauncher.launch(signInIntent)
    }

    private fun registerActivityForGoogleSignIn(){

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->

                val resultCode =result.resultCode
                val data = result.data


                if(resultCode == RESULT_OK &&  data != null) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(data)

                    firebaseSignInWithGoogle(task)
                }
            })

    }

    private fun firebaseSignInWithGoogle(task :Task<GoogleSignInAccount>){
     try {
         val account : GoogleSignInAccount =task.getResult(ApiException::class.java)
         Toast.makeText(applicationContext,"Welcome to Quiz",Toast.LENGTH_SHORT).show()

         val intent =Intent(this,MainActivity::class.java)
         startActivity(intent)
         finish()
         firebaseGoogleAccount(account)

     } catch ( e : ApiException){

         Toast.makeText(applicationContext,e.localizedMessage,Toast.LENGTH_SHORT).show()

     }

    }

   private fun  firebaseGoogleAccount(account : GoogleSignInAccount){
       val authCredential = GoogleAuthProvider.getCredential(account.idToken,null)
       auth.signInWithCredential(authCredential).addOnCompleteListener { task ->

           if (task.isSuccessful){
             //  val user = auth.currentUser

           } else{

           }
       }
   }
}