package pl.swiatek.restaurantclientapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.util.*

class ChooseTableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_table)
        val data=intent.getStringArrayExtra("selectedDate")
        val data2= Date(data!![0].toInt(),data[1].toInt(),data[2].toInt(),data[3].toInt(),data[4].toInt())
        val data3=Date(2023,10,14,12,31)
        if(data2.before(data3)){
            Toast.makeText(applicationContext,"Przed",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext, "Po lub rowno", Toast.LENGTH_SHORT).show()
        }


    }
}