package pl.swiatek.restaurantclientapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalDateTime.*
import java.time.format.DateTimeFormatter
import java.util.*

class ChooseDateActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    var day=0;
    var month=0;
    var year=0;
    var hour =0;
    var minute=0;

    var savedDay=0;
    var savedMonth=0;
    var savedYear=0;
    var savedHour =0;
    var savedMinute=0;

    private lateinit var dateText:TextView
    private lateinit var currentDate:LocalDateTime
    private lateinit var datePicked:LocalDateTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_date)
        dateText=findViewById(R.id.dateText)
        currentDate = now()

    }

    private fun getDateTime(){
        val cal=Calendar.getInstance()
        day=cal.get(Calendar.DAY_OF_MONTH)
        month=cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        hour=cal.get(Calendar.HOUR_OF_DAY)
        minute=cal.get(Calendar.MINUTE)
    }

    fun pickDateClick(view:View){
        getDateTime()
        DatePickerDialog(this,this,year,month,day).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay=dayOfMonth
        savedMonth=month+1
        savedYear=year

        getDateTime()
        TimePickerDialog(this,this,hour,minute,true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour=hourOfDay
        savedMinute=minute
        dateText.text="$savedDay-$savedMonth-$savedYear, $savedHour:$savedMinute"
        datePicked=of(savedYear,savedMonth,savedDay,savedHour,savedMinute)
    }

    fun onBookTableClick(view: View){
        if(savedYear==0){
            Toast.makeText(applicationContext,"Pick date!",Toast.LENGTH_SHORT).show()
            return
        }

            if(datePicked.isBefore(currentDate)){
                Toast.makeText(applicationContext,"Invalid date!",Toast.LENGTH_SHORT).show()
                return
            }

            val intent = Intent(this, ChooseTableActivity::class.java)
            intent.putExtra(
                "selectedDate",
                arrayOf(savedYear.toString(), savedMonth.toString(), savedDay.toString(), savedHour.toString(), savedMinute.toString())
            )
            startActivity(intent)

    }

}