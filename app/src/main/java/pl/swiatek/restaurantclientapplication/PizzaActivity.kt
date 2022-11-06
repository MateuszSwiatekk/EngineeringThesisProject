package pl.swiatek.restaurantclientapplication

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PizzaActivity : AppCompatActivity() {
    private lateinit var ordersDatabase: DatabaseReference
    private lateinit var pizzaListDatabase:DatabaseReference
    private lateinit var key:String
    private lateinit var listViewPizza : ListView
    private lateinit var foodList : ArrayList<FoodItem>
    private lateinit var totalPrice:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza)
        key= intent.getStringExtra("orderKey").toString()
        totalPrice=findViewById(R.id.totalPrice)
        listViewPizza = findViewById(R.id.listViewPizzas)
        foodList = arrayListOf()
        pizzaListDatabase=FirebaseDatabase.getInstance().getReference("FoodItems")
        val queryBookedTables = pizzaListDatabase.orderByChild("type").equalTo("Pizza")
        queryBookedTables.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                foodList.clear()
                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){
                        val food = userSnapshot.getValue(FoodItem::class.java)
                        foodList.add(food!!)
                    }
                    val adapter = AdapterFood(applicationContext,R.layout.food_item,foodList)
                    listViewPizza.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        val itemsOrdered=FirebaseDatabase.getInstance().getReference("Orders").child(key).child("Items").orderByChild("price").startAt(0.00)

        itemsOrdered.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var price=0.00
                    for(snapshot in snapshot.children){
                        val item=snapshot.getValue(FoodItem::class.java)
                        price += (item!!.getPrice()*item.getQuantity())
                    }
                    totalPrice.setText(price.toString())
                    FirebaseDatabase.getInstance().getReference("Orders").child(key).child("total").setValue(totalPrice.text.toString().toDouble())
                }else{
                    totalPrice.setText("0.01")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        listViewPizza.setOnItemClickListener { parent, view, position, id ->
            val item=foodList[position]

            val dialogBinding=layoutInflater.inflate(R.layout.dialog_add_to_order,null)
            val dialog=Dialog(this)
            dialog.setContentView(dialogBinding)
            dialog.setCancelable(false)
            dialog.show()

            val cancelBtn=dialogBinding.findViewById<Button>(R.id.cancelButton)
            val addBtn=dialogBinding.findViewById<Button>(R.id.addButton)
            val numberPicker=dialogBinding.findViewById<NumberPicker>(R.id.quantityPicker)
            numberPicker.minValue=1
            numberPicker.maxValue=9
            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }
            addBtn.setOnClickListener {
                ordersDatabase=FirebaseDatabase.getInstance().getReference("Orders").child(key).child("Items")
                val item=FoodItem(item.foodName,item.type ,item.price,numberPicker.value)
                ordersDatabase.child(ordersDatabase.push().key!!)
                    .setValue(item)
                dialog.dismiss()
            }
        }
    }
}


//dodanie jedzenia do orderu
//ordersDatabase=FirebaseDatabase.getInstance().getReference("Orders").child(key).child("Items")
//val item=FoodItem("Kapricziosa", "Pizza",21.99,2)
//        ordersDatabase.child(ordersDatabase.push().key!!)
//            .setValue(item)

//dodanie przedmiotu do listy jedzenia
//pizzaListDatabase=FirebaseDatabase.getInstance().getReference("FoodItems")
//val food=FoodItem("Pizza 1","Pizza",21.99,0)
//pizzaListDatabase.child(pizzaListDatabase.push().key!!)
//.setValue(food)