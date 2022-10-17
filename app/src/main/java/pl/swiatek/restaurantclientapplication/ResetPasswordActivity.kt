package pl.swiatek.restaurantclientapplication

import android.content.Intent
import android.media.MediaCodec
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var resetEmail:EditText
    private lateinit var resetBtn:Button
    private lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        resetEmail=findViewById(R.id.resetEmail)
        resetBtn=findViewById(R.id.resetBtn)
        progressBar=findViewById(R.id.progressBar3)
    }


    fun resetClick(view: View){
        val authData=FirebaseAuth.getInstance()
        val email=resetEmail.text.toString().trim()
        if(email.isEmpty()){
            resetEmail.error="Email address needed"
            resetEmail.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            resetEmail.error="Invalid email address"
            resetEmail.requestFocus()
            return
        }
        progressBar.visibility=View.VISIBLE
        authData.sendPasswordResetEmail(email).addOnCompleteListener {task->
            if(task.isSuccessful){
                Toast.makeText(applicationContext,"Email sent!",Toast.LENGTH_SHORT).show()
                progressBar.visibility=View.INVISIBLE
                val intent=Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext,"Something went wrong!", Toast.LENGTH_SHORT).show()
                progressBar.visibility=View.INVISIBLE
            }
        }
    }

}