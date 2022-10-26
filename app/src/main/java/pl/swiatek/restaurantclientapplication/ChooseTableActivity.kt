package pl.swiatek.restaurantclientapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.util.*
import javax.xml.validation.Validator

class ChooseTableActivity : AppCompatActivity() {
    private lateinit var email:String
    private lateinit var db:DatabaseReference
    private lateinit var userId:String
    private lateinit var data2:String
    private lateinit var data3:String
    private lateinit var bookedTablesData:DatabaseReference
    private lateinit var bookedTables:MutableSet<String>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_table)
        val data=intent.getStringArrayExtra("selectedDate")
        data2 = (data!![0]).toString()+(data[1]).toString()+(data[2]).toString()+(data[3]).toString()+(data[4]).toString()
        data3 = (data[0]).toString()+(data[1]).toString()+(data[2]).toString()+(data[3].toInt()+1).toString()+(data[4].toInt()).toString()

        db=FirebaseDatabase.getInstance().getReference("BookedTables")
        bookedTablesData=FirebaseDatabase.getInstance().getReference("BookedTables")
        bookedTables= mutableSetOf()

        val query = bookedTablesData.orderByChild("endDate").startAt(data2).endAt(data3)
        query.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChildren()){
                    for(snapData in snapshot.children){
                        val tableData=snapData.getValue(BookedTable::class.java)
                        bookedTables.add(tableData!!.tableNumber)
                    }
                    if(bookedTables.contains("1")){
                        Toast.makeText(applicationContext, "Stol 1 zajenty!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(applicationContext, "Stol 1 wolny!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        userId= FirebaseAuth.getInstance().currentUser!!.uid
        val databaseUsers=FirebaseDatabase.getInstance().getReference("Users")

        databaseUsers.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user: User? = dataSnapshot.getValue(User::class.java)
                email = user!!.email
                Toast.makeText(applicationContext, email, Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("ERR","Couldn't get email")
            }

        })
    }

    fun table1Click(view: View){
        val booking=BookedTable("1",data2,data3,email)
        db.child(db.push().key!!)
            .setValue(booking)
    }
    fun table2Click(view: View){

    }
    fun table3Click(view: View){

    }
}