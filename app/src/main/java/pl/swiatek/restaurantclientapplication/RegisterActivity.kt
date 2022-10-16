package pl.swiatek.restaurantclientapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    private lateinit var editTextEmail:EditText
    private lateinit var editTextName:EditText
    private lateinit var editTextPassword:EditText
    private lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()

        editTextEmail=findViewById(R.id.registerEmailEdit)
        editTextName=findViewById(R.id.registerNameEdit)
        editTextPassword=findViewById(R.id.registerPasswordEdit)
        progressBar=findViewById(R.id.progressBar2)
    }

    fun registerClicked(view: View){
        val email=editTextEmail.text.toString().trim()
        val password=editTextPassword.text.toString().trim()
        val name=editTextName.text.toString().trim()

        if(name.isEmpty()){
            editTextName.error = "Name is required!"
            editTextName.requestFocus()
            return
        }
        if(email.isEmpty()){
            editTextEmail.error = "Email is required!"
            editTextEmail.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.error = "Invalid email"
            editTextEmail.requestFocus()
            return
        }
        if(password.isEmpty()){
            editTextPassword.error = "Password is required!"
            editTextPassword.requestFocus()
            return
        }
        if(password.length<6){
            editTextPassword.error="Password too short"
            editTextPassword.requestFocus()
            return
        }

        progressBar.visibility=View.VISIBLE

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                val firebaseUser:FirebaseUser=task.result!!.user!!
                Toast.makeText(applicationContext,"Registration complete!",Toast.LENGTH_SHORT).show()
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(applicationContext,"Something went wrong!",Toast.LENGTH_SHORT).show()
                progressBar.visibility=View.INVISIBLE
            }
        }
    }

}