package pl.swiatek.restaurantclientapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PizzaActivity : AppCompatActivity() {
    private lateinit var ordersDatabase: DatabaseReference
    private lateinit var pizzaListDatabase:DatabaseReference
    private lateinit var key:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza)
        key= intent.getStringExtra("orderKey").toString()
        pizzaListDatabase=FirebaseDatabase.getInstance().getReference("FoodItems")
        val food=FoodItem("Pizza 1","Pizza",21.99,0)
        pizzaListDatabase.child(pizzaListDatabase.push().key!!)
            .setValue(food)

    }
}
//dodanie jedzenia do orderu
//ordersDatabase=FirebaseDatabase.getInstance().getReference("Orders").child(key).child("Items")
//val item=FoodItem("Kapricziosa", "Pizza",21.99,2)
//        ordersDatabase.child(ordersDatabase.push().key!!)
//            .setValue(item)