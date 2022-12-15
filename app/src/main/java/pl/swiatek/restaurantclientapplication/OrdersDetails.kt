package pl.swiatek.restaurantclientapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*


class OrdersDetails : AppCompatActivity() {
    private lateinit var orderedFood: ArrayList<FoodItem>
    private lateinit var orderListView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders_details)

        val data = intent.getStringExtra("total")
        orderedFood= arrayListOf()
        orderListView=findViewById(R.id.ordersDetailList)
        doSmth()

    }
    fun doSmth(){

        val itemsOrdered =
            FirebaseDatabase.getInstance().getReference("Orders").child(intent.getStringExtra("total").toString()).child("Items")
                .orderByChild("price").startAt(0.00)

        itemsOrdered.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snapshot in snapshot.children) {
                        val item = snapshot.getValue(FoodItem::class.java)
                        orderedFood.add(item!!)
                    }
                    val adapter = AdapterCheckout(applicationContext,R.layout.checkout_item,orderedFood)
                    orderListView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun cancelOrder(view: View){
        FirebaseDatabase.getInstance().getReference("Orders").child(intent.getStringExtra("total").toString()).removeValue()
        Toast.makeText(applicationContext,"Order has been cancelled",Toast.LENGTH_SHORT).show()
        finish()
    }
}