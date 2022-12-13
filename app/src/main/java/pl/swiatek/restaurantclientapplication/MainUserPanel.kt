package pl.swiatek.restaurantclientapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainUserPanel : AppCompatActivity() {

    private lateinit var welcomeEditText: TextView
    private lateinit var email:String
    private lateinit var progressBar:ProgressBar
    private lateinit var viewReservationsBtn:Button
    private lateinit var bookingBtn:Button
    private lateinit var logoutBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user_panel)
        welcomeEditText=findViewById(R.id.welcomeText)
        progressBar=findViewById(R.id.progressBar4)
        viewReservationsBtn=findViewById(R.id.viewReservationsBtn)
        bookingBtn=findViewById(R.id.bookingBtn)
        logoutBtn=findViewById(R.id.logoutBtn)

        viewReservationsBtn.visibility=View.INVISIBLE
        welcomeEditText.visibility=View.INVISIBLE
        bookingBtn.visibility=View.INVISIBLE
        logoutBtn.visibility=View.INVISIBLE
        progressBar.visibility=View.VISIBLE
        val userId=FirebaseAuth.getInstance().currentUser!!.uid
        val database=FirebaseDatabase.getInstance().getReference("Users")

        database.child(userId).addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user: User? = dataSnapshot.getValue(User::class.java)
                val name = user!!.name
                email = user!!.email
                welcomeEditText.text = "Welcome $name"
                viewReservationsBtn.visibility=View.VISIBLE
                bookingBtn.visibility=View.VISIBLE
                logoutBtn.visibility=View.VISIBLE
                progressBar.visibility=View.INVISIBLE
                welcomeEditText.visibility=View.VISIBLE
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

    fun bookingClick(view: View){
        val intent=Intent(this,ChooseDateActivity::class.java)
        startActivity(intent)
    }

    fun viewReservationsClick(view: View){
        val intent=Intent(this,ViewReservationsActivity::class.java)
        intent.putExtra("email",email)
        startActivity(intent)
    }

    fun menuAndOrderClick(view: View){
        val intent=Intent(this,MenuAndOrderActivity::class.java)
        startActivity(intent)
    }

    fun viewOrdersClick(view: View){
        val intent=Intent(this,ViewOrdersActivity::class.java)
        startActivity(intent)
    }

}