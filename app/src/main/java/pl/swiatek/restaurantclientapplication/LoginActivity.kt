package pl.swiatek.restaurantclientapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText:EditText
    private lateinit var passwordEditText:EditText
    private lateinit var loginButton:Button
    private lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText=findViewById(R.id.emailEdit)
        passwordEditText=findViewById(R.id.passwordEdit)
        loginButton=findViewById(R.id.loginButton)
        progressBar=findViewById(R.id.progressBar)
    }

    fun registerClick(view: View){
    val intent= Intent(this,RegisterActivity::class.java)
    startActivity(intent)
    }

    fun loginClick(view: View){

        val email=emailEditText.text.toString().trim()
        val password=passwordEditText.text.toString().trim()

        if(email.isEmpty()){
            emailEditText.error="Email is required"
            emailEditText.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.error="Invalid email"
            emailEditText.requestFocus()
            return
        }
        if(password.isEmpty()) {
            passwordEditText.error="Password required"
            passwordEditText.requestFocus()
            return
        }
        progressBar.visibility=View.VISIBLE
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener{task->
            if(task.isSuccessful){
                Toast.makeText(applicationContext,"Logged in",Toast.LENGTH_SHORT).show()
                progressBar.visibility=View.INVISIBLE
            }else{
                Toast.makeText(applicationContext,"Something went wrong!", Toast.LENGTH_SHORT).show()
                progressBar.visibility=View.INVISIBLE
            }
        }


    }

}