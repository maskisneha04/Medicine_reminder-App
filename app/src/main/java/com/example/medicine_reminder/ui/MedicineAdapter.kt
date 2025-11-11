package com.example.medicine_reminder.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicine_reminder.R

data class Medicine(
    val name: String,
    val time: String,
    var isTaken: Boolean = false
)

class MedicineAdapter(
    private val medicines: MutableList<Medicine>,
    private val onDelete: (Medicine) -> Unit
) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    inner class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val medicineInfo: TextView = itemView.findViewById(R.id.medicineInfo)
        val takenSwitch: Switch = itemView.findViewById(R.id.takenSwitch)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicine, parent, false)
        return MedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = medicines[position]
        holder.medicineInfo.text = "${medicine.name} - ${medicine.time}"

        holder.takenSwitch.isChecked = medicine.isTaken
        holder.takenSwitch.setOnCheckedChangeListener { _, isChecked ->
            medicine.isTaken = isChecked
        }

        holder.deleteBtn.setOnClickListener {
            onDelete(medicine)
        }
    }

    override fun getItemCount() = medicines.size
}
