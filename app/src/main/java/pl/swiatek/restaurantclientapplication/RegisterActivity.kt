package pl.swiatek.restaurantclientapplication

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

private var mAuth: FirebaseAuth? = null

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextEmail:EditText
    private lateinit var editTextName:EditText
    private lateinit var editTextPassword:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()

        editTextEmail=findViewById(R.id.registerEmailEdit)
        editTextName=findViewById(R.id.registerNameEdit)
        editTextPassword=findViewById(R.id.registerPasswordEdit)

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

    }

}