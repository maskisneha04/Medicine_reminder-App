package com.example.medicine_reminder.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.medicine_reminder.R
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import java.util.Calendar

class AddMedicineActivity : AppCompatActivity() {

    private lateinit var medicineNameInput: EditText
    private lateinit var timePicker: TimePicker
    private lateinit var addBtn: Button
    private lateinit var takenBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine)

        medicineNameInput = findViewById(R.id.medicineNameInput)
        timePicker = findViewById(R.id.timePicker)
        addBtn = findViewById(R.id.addBtn)
        takenBtn = findViewById(R.id.takenBtn)

        timePicker.setIs24HourView(true)

        addBtn.setOnClickListener {
            val name = medicineNameInput.text.toString()
            val hour = timePicker.hour
            val minute = timePicker.minute
            val time = String.format("%02d:%02d", hour, minute)

            val resultIntent = Intent()
            resultIntent.putExtra("medicine_name", name)
            resultIntent.putExtra("medicine_time", time)
            resultIntent.putExtra("status", "Added")
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        takenBtn.setOnClickListener {
            val name = medicineNameInput.text.toString()
            val hour = timePicker.hour
            val minute = timePicker.minute
            val time = String.format("%02d:%02d", hour, minute)

            val resultIntent = Intent()
            resultIntent.putExtra("medicine_name", name)
            resultIntent.putExtra("medicine_time", time)
            resultIntent.putExtra("status", "Taken")
            setResult(RESULT_OK, resultIntent)

            // Schedule notification
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, ReminderReceiver::class.java).apply {
                putExtra("medicine_name", name)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                this, System.currentTimeMillis().toInt(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
            }

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

            finish()
        }
    }
}
