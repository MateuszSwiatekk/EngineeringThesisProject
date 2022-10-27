package pl.swiatek.restaurantclientapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.properties.Delegates

class ChooseTableActivity : AppCompatActivity() {
    private lateinit var email:String
    private lateinit var userId:String
    private lateinit var data2:String
    private lateinit var data3:String
    private lateinit var bookedTablesData:DatabaseReference
    private lateinit var bookedTables:MutableSet<String>
    private var bookedAmount by Delegates.notNull<Long>()

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

        userId= FirebaseAuth.getInstance().currentUser!!.uid
        email= FirebaseAuth.getInstance().currentUser!!.email.toString()
        bookedTablesData=FirebaseDatabase.getInstance().getReference("BookedTables")
        bookedTables= mutableSetOf()

        val queryGetBookedTables = bookedTablesData.orderByChild("endDate").startAt(data2).endAt(data3)
        queryGetBookedTables.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChildren()){
                    for(snapData in snapshot.children){
                        val tableData=snapData.getValue(BookedTable::class.java)
                        bookedTables.add(tableData!!.tableNumber)
                    }
                    for(i in 0..9){
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

        val queryAmountBooked = bookedTablesData.orderByChild("email").equalTo(email)
        queryAmountBooked.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChildren()){
                    bookedAmount=snapshot.childrenCount
                }else{
                    bookedAmount=0
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun bookTable(tableNum:String){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you really want to book this table?")
        builder.setTitle("Confirmation")
        builder.setCancelable(false)

        builder.setPositiveButton("Yes") {
                dialog, which -> val booking=BookedTable(tableNum,data2,data3,email)
            bookedTablesData.child(bookedTablesData.push().key!!)
                .setValue(booking)
        }

        builder.setNegativeButton("No") {
                dialog, which -> dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun table1Click(view: View){
        if(bookedAmount<3) {
            bookTable("1")
        }else{
            Toast.makeText(applicationContext, "Can't make more reservations!", Toast.LENGTH_SHORT).show()
        }
    }
    fun table2Click(view: View){
        if(bookedAmount<3) {
            bookTable("2")
        }else{
            Toast.makeText(applicationContext, "Can't make more reservations!", Toast.LENGTH_SHORT).show()
        }
    }
    fun table3Click(view: View){
        if(bookedAmount<3) {
            bookTable("3")
        }else{
            Toast.makeText(applicationContext, "Can't make more reservations!", Toast.LENGTH_SHORT).show()
        }
    }
    fun table4Click(view: View){
        if(bookedAmount<3) {
            bookTable("4")
        }else{
            Toast.makeText(applicationContext, "Can't make more reservations!", Toast.LENGTH_SHORT).show()
        }
    }
    fun table5Click(view: View){
        if(bookedAmount<3) {
            bookTable("5")
        }else{
            Toast.makeText(applicationContext, "Can't make more reservations!", Toast.LENGTH_SHORT).show()
        }
    }
    fun table6Click(view: View){
        if(bookedAmount<3) {
            bookTable("6")
        }else{
            Toast.makeText(applicationContext, "Can't make more reservations!", Toast.LENGTH_SHORT).show()
        }
    }
    fun table7Click(view: View){
        if(bookedAmount<3) {
            bookTable("7")
        }else{
            Toast.makeText(applicationContext, "Can't make more reservations!", Toast.LENGTH_SHORT).show()
        }
    }
    fun table8Click(view: View){
        if(bookedAmount<3) {
            bookTable("8")
        }else{
            Toast.makeText(applicationContext, "Can't make more reservations!", Toast.LENGTH_SHORT).show()
        }
    }
    fun table9Click(view: View){
        if(bookedAmount<3) {
            bookTable("9")
        }else{
            Toast.makeText(applicationContext, "Can't make more reservations!", Toast.LENGTH_SHORT).show()
        }
    }
    fun table10Click(view: View){
        if(bookedAmount<3) {
            bookTable("10")
        }else{
            Toast.makeText(applicationContext, "Can't make more reservations!", Toast.LENGTH_SHORT).show()
        }
    }
}