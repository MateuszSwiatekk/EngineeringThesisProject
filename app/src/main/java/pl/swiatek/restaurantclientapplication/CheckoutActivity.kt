package pl.swiatek.restaurantclientapplication

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.math.roundToInt

class CheckoutActivity : AppCompatActivity() {
    private lateinit var key: String
    private lateinit var totalPrice: TextView
    private lateinit var orderedFood: ArrayList<FoodItem>
    private lateinit var orderListView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        orderListView = findViewById(R.id.checkoutListView)
        totalPrice = findViewById(R.id.totalCheckout)
        val data = intent.getStringExtra("orderKey")
        key = data.toString()
        orderedFood = arrayListOf()
        orderListView.setOnItemClickListener { parent, view, position, id ->
            buildConfirmation(position)
        }
        val itemsOrdered =
            FirebaseDatabase.getInstance().getReference("Orders").child(key).child("Items")
                .orderByChild("price").startAt(0.00)

        itemsOrdered.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orderedFood.clear()
                if (snapshot.exists()) {
                    var price = 0.00
                    var roundoff = "0.00"
                    for (snapshot in snapshot.children) {
                        val item = snapshot.getValue(FoodItem::class.java)
                        orderedFood.add(item!!)
                        price += (item!!.getPrice() * item.getQuantity())
                        price = (price * 100.00)
                        roundoff = String.format(
                            Locale.ENGLISH,
                            "%.2f",
                            price.roundToInt().toDouble() / 100
                        )
                        price = roundoff.toDouble()
                    }
                    val adapter =
                        AdapterCheckout(applicationContext, R.layout.checkout_item, orderedFood)
                    orderListView.adapter = adapter

                    totalPrice.setText(roundoff)
                    FirebaseDatabase.getInstance().getReference("Orders").child(key).child("total")
                        .setValue(totalPrice.text.toString().toDouble())
                } else {
                    totalPrice.setText("0.00")
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
        val food= orderedFood[position]
        val db=FirebaseDatabase.getInstance().getReference("Orders").child(key).child("Items")
        val query = db.orderByChild("foodName").equalTo(food.foodName)
        query.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapData in snapshot.children){
                        val key = snapData.key!!
                        db.child(key).removeValue()
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun onOrderClick(view: View){
        FirebaseDatabase.getInstance().getReference("Orders").child(key).child("finished")
            .setValue(true)
        Toast.makeText(applicationContext,"Your order has been placed!",Toast.LENGTH_SHORT).show()
        finish()
        val intent = Intent(this,MainUserPanel::class.java)
        startActivity(intent)
    }

    }
