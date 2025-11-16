package com.example.medicine_reminder.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicine_reminder.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var medicineRecycler: RecyclerView
    private lateinit var addMedicineBtn: FloatingActionButton
    private val medicineList = mutableListOf<Medicine>()
    private lateinit var adapter: MedicineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        medicineRecycler = findViewById(R.id.medicineList)
        val addMedicineBtn = findViewById<Button>(R.id.addMedicineBtn)


        adapter = MedicineAdapter(medicineList) { medicine ->
            medicineList.remove(medicine)
            adapter.notifyDataSetChanged()
        }

        medicineRecycler.layoutManager = LinearLayoutManager(this)
        medicineRecycler.adapter = adapter

        addMedicineBtn.setOnClickListener {
            val intent = Intent(this, AddMedicineActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val name = data?.getStringExtra("medicine_name") ?: ""
            val time = data?.getStringExtra("medicine_time") ?: ""
            val newMedicine = Medicine(name, time)
            medicineList.add(newMedicine)
            adapter.notifyDataSetChanged()
        }
    }
}
