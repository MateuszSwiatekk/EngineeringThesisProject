package pl.swiatek.restaurantclientapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timerTask


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startSecondActivity()
    }
    private fun startSecondActivity(){
        if(!isDestroyed) {
            val intent = Intent(this, LoginActivity::class.java)
            val timTask= timerTask {
                if(!isDestroyed){
                    startActivity(intent)
                    finish()
                }
            }
            val timer=Timer()
            timer.schedule(timTask,1500)
        }
    }
}