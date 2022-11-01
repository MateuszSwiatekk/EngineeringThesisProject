package pl.swiatek.restaurantclientapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ViewReservationsActivity : AppCompatActivity() {

    private lateinit var db : DatabaseReference
    private lateinit var recyclerBookedList : RecyclerView
    private lateinit var bookedTableArrayList : ArrayList<BookedTable>
    private lateinit var email:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reservations)

        recyclerBookedList = findViewById(R.id.recyclerBookedList)
        recyclerBookedList.layoutManager = LinearLayoutManager(this)
        recyclerBookedList.setHasFixedSize(true)
        email= intent.getStringExtra("email").toString()
        bookedTableArrayList = arrayListOf()
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
                    val adapter=BookedTablesAdapter(bookedTableArrayList)
                    recyclerBookedList.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
//    fun cancelClicked(view:View){
//        val booking= bookedTableArrayList[position]
//        val query = db.orderByChild("email").equalTo(email)
//        query.addValueEventListener(object:ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (snapData in snapshot.children){
//                    val cos=snapData.getValue(BookedTable::class.java)
//                    if(cos!!.tableNumber.equals(booking.tableNumber) && cos.endDate.equals(booking.endDate)){
//                        val key = snapData.key!!
//                        db.child(key).removeValue()
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//
//        })
//    }
}