package pl.swiatek.restaurantclientapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MenuAndOrderActivity : AppCompatActivity() {

    private lateinit var ordersDatabase: DatabaseReference
    private lateinit var key:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_and_order)

        ordersDatabase = FirebaseDatabase.getInstance().getReference("Orders")
        val order=Order("1", "ms.swiatek@gmail.com",21.01,false)
        key=ordersDatabase.push().key!!
        ordersDatabase.child(key)
            .setValue(order)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ordersDatabase.child(key).removeValue()
    }

    fun pizzaClicked(view:View){
        val intent= Intent(this,PizzaActivity::class.java)
        intent.putExtra("orderKey",key)
        startActivity(intent)
    }

    fun saladsClicked(view: View){
        val intent= Intent(this,SaladsActivity::class.java)
        intent.putExtra("orderKey",key)
        startActivity(intent)
    }

    fun soupsClicked(view: View){
        val intent= Intent(this,SoupsActivity::class.java)
        intent.putExtra("orderKey",key)
        startActivity(intent)
    }

    fun drinksClicked(view: View){
        val intent= Intent(this,DrinksActivity::class.java)
        intent.putExtra("orderKey",key)
        startActivity(intent)
    }
}