package pl.swiatek.restaurantclientapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.LocalDateTime.now

class ViewReservationsActivity : AppCompatActivity() {

    private lateinit var db : DatabaseReference
    private lateinit var listViewBookedList : ListView
    private lateinit var bookedTableArrayList : ArrayList<BookedTable>
    private lateinit var email:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reservations)

        listViewBookedList = findViewById(R.id.listViewReservations)
        email= intent.getStringExtra("email").toString()
        bookedTableArrayList = arrayListOf()
        listViewBookedList.setOnItemClickListener { parent, view, position, id ->
            buildConfirmation(position)
        }
        getUserData()
    }

    private fun getUserData() {

        db = FirebaseDatabase.getInstance().getReference("BookedTables")
        val queryBookedTables = db.orderByChild("email").equalTo(email)
        queryBookedTables.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                bookedTableArrayList.clear()
                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(BookedTable::class.java)
                        bookedTableArrayList.add(user!!)
                    }
                    val adapter = AdapterBookedTables(applicationContext,R.layout.booked_item,bookedTableArrayList)
                    listViewBookedList.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun buildConfirmation(position: Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to delete this reservation?")

        builder.setTitle("Confirmation")
        builder.setCancelable(false)

        builder.setPositiveButton("Yes") {
                dialog, which -> cancelTable(position)
        }

        builder.setNegativeButton("No") {
                dialog, which -> dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun cancelTable(position:Int){
        val booking= bookedTableArrayList[position]
        val query = db.orderByChild("email").equalTo(email)
        query.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapData in snapshot.children){
                    val child=snapData.getValue(BookedTable::class.java)
                    if(child!!.tableNumber.equals(booking.tableNumber) && child.endDate.equals(booking.endDate)){
                        val key = snapData.key!!
                        db.child(key).removeValue()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}