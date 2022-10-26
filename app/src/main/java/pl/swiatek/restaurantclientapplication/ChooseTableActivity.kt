package pl.swiatek.restaurantclientapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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

        val table1Btn=findViewById<Button>(R.id.table1)
        val table2Btn=findViewById<Button>(R.id.table2)
        val table3Btn=findViewById<Button>(R.id.table3)
        val table4Btn=findViewById<Button>(R.id.table4)
        val table5Btn=findViewById<Button>(R.id.table5)
        val table6Btn=findViewById<Button>(R.id.table6)
        val table7Btn=findViewById<Button>(R.id.table7)
        val table8Btn=findViewById<Button>(R.id.table8)
        val table9Btn=findViewById<Button>(R.id.table9)
        val table10Btn=findViewById<Button>(R.id.table10)

        val arrayTableBtns= arrayOf(table1Btn,table2Btn,table3Btn,table4Btn,table5Btn,table6Btn,table7Btn,table8Btn,table9Btn,table10Btn)

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
                    for(i in 0..10){
                        if (bookedTables.contains((i+1).toString())) {
                            arrayTableBtns[i].setBackgroundColor(Color.GRAY)
                            arrayTableBtns[i].isClickable = false
                        }
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
        val booking=BookedTable("2",data2,data3,email)
        db.child(db.push().key!!)
            .setValue(booking)
    }
    fun table3Click(view: View){
        val booking=BookedTable("3",data2,data3,email)
        db.child(db.push().key!!)
            .setValue(booking)
    }
    fun table4Click(view: View){
        val booking=BookedTable("4",data2,data3,email)
        db.child(db.push().key!!)
            .setValue(booking)
    }
    fun table5Click(view: View){
        val booking=BookedTable("5",data2,data3,email)
        db.child(db.push().key!!)
            .setValue(booking)
    }
    fun table6Click(view: View){
        val booking=BookedTable("6",data2,data3,email)
        db.child(db.push().key!!)
            .setValue(booking)
    }
    fun table7Click(view: View){
        val booking=BookedTable("7",data2,data3,email)
        db.child(db.push().key!!)
            .setValue(booking)
    }
    fun table8Click(view: View){
        val booking=BookedTable("8",data2,data3,email)
        db.child(db.push().key!!)
            .setValue(booking)
    }
    fun table9Click(view: View){
        val booking=BookedTable("9",data2,data3,email)
        db.child(db.push().key!!)
            .setValue(booking)
    }
    fun table10Click(view: View){
        val booking=BookedTable("10",data2,data3,email)
        db.child(db.push().key!!)
            .setValue(booking)
    }
}