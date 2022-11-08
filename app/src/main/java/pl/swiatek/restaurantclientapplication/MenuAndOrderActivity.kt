package pl.swiatek.restaurantclientapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*

class MenuAndOrderActivity : AppCompatActivity() {

    private lateinit var ordersDatabase: DatabaseReference
    private lateinit var key:String
    private lateinit var totalPriceMain:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_and_order)
        totalPriceMain=findViewById(R.id.totalPriceMain)

        ordersDatabase = FirebaseDatabase.getInstance().getReference("Orders")
        val order=Order("1", "ms.swiatek@gmail.com",00.00,false)
        key=ordersDatabase.push().key!!
        ordersDatabase.child(key)
            .setValue(order)


        val queryPrice = ordersDatabase.child(key)
        queryPrice.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val order=snapshot.getValue(Order::class.java)
                    totalPriceMain.setText(order!!.getTotal().toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }



    override fun onBackPressed() {
    buildDialog()
    }

    fun pizzaClicked(view:View){
        val intent= Intent(this,MenuItemsListActivity::class.java)
        intent.putExtra("orderKey",arrayOf(key,"Pizza"))
        startActivity(intent)
    }

    fun saladsClicked(view: View){
        val intent= Intent(this,MenuItemsListActivity::class.java)
        intent.putExtra("orderKey",arrayOf(key,"Salad"))
        startActivity(intent)
    }

    fun soupsClicked(view: View){
        val intent= Intent(this,MenuItemsListActivity::class.java)
        intent.putExtra("orderKey",arrayOf(key,"Soup"))
        startActivity(intent)
    }

    fun drinksClicked(view: View){
        val intent= Intent(this,MenuItemsListActivity::class.java)
        intent.putExtra("orderKey",arrayOf(key,"Drink"))
        startActivity(intent)
    }

    fun checkoutClick(view: View){

    }

    fun buildDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you really want to cancel your order?")
        builder.setTitle("Confirmation")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") {
                dialog, which -> ordersDatabase.child(key).removeValue()
            finish()
        }
        builder.setNegativeButton("No") {
                dialog, which -> dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}