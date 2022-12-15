package pl.swiatek.restaurantclientapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class ViewOrdersActivity : AppCompatActivity() {
    private lateinit var listViewOrders: ListView
    private lateinit var orderList: ArrayList<Order>
    private lateinit var keyList:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_orders)

        listViewOrders = findViewById(R.id.listViewOrders)
        keyList= arrayListOf()
        listViewOrders.setOnItemClickListener { parent, view, position, id ->
            val intent= Intent(this,OrdersDetails::class.java)
                .putExtra("total",keyList[position])
            startActivity(intent)
        }
        orderList = arrayListOf()

        val db = FirebaseDatabase.getInstance().getReference("Orders")
        val queryOrders = db.orderByChild("finished").equalTo(true)
        queryOrders.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                orderList.clear()
                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(Order::class.java)
                        keyList.add(userSnapshot.key.toString())
                        orderList.add(user!!)
                    }
                }
                val adapter = AdapterOrders(applicationContext,R.layout.order_item,orderList)
                listViewOrders.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}