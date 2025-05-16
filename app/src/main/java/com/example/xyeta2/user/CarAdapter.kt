package com.example.xyeta2.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.xyeta2.R
import com.example.xyeta2.models.Car

class CarAdapter(
    private val onItemClick: (Car) -> Unit
) : ListAdapter<Car, CarAdapter.ViewHolder>(CarDiffCallback()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(car: Car) {
            itemView.apply {
                findViewById<TextView>(R.id.tvCarModel).text = car.model
                findViewById<TextView>(R.id.tvPrice).text = "Цена: ${car.pricePerHour} ₽/час"
                setOnClickListener { onItemClick(car) }
            }
        }
    }

    class CarDiffCallback : DiffUtil.ItemCallback<Car>() {
        override fun areItemsTheSame(oldItem: Car, newItem: Car) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Car, newItem: Car) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_car, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}