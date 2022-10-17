package pl.swiatek.restaurantclientapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class MainUserPanel : AppCompatActivity() {

    private lateinit var welcomeEditText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user_panel)
        welcomeEditText=findViewById(R.id.welcomeText)

        val userId=FirebaseAuth.getInstance().currentUser!!.uid
        val database=FirebaseDatabase.getInstance().getReference("Users")

        database.child(userId).addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user: User? = dataSnapshot.getValue(User::class.java)
                val name = user!!.name
                welcomeEditText.text = "Welcome $name"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("ERR","Couldn't get username")
            }

        })


    }

    fun logoutClick(view: View){
        FirebaseAuth.getInstance().signOut()
        val intent=Intent(this,LoginActivity::class.java)
        makeText(applicationContext,"Logged out!",Toast.LENGTH_SHORT).show()
        startActivity(intent)
        finish()
    }

}