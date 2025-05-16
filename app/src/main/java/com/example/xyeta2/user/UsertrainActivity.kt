package com.example.xyeta2.user

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xyeta2.R
import com.example.xyeta2.models.Car
import com.example.xyeta2.utils.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class UsertrainActivity : AppCompatActivity() {
    private lateinit var adapter: CarAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usertrain)
        recyclerView = findViewById(R.id.recyclerView)

        setupRecyclerView()
        loadCars()
    }

    private fun setupRecyclerView() {
        adapter = CarAdapter { car ->
            if (car.available) {
                FirebaseHelper.rentCar(getCurrentUserId(), car.id)
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadCars() {
        FirebaseHelper.getCars(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cars = snapshot.children.mapNotNull {
                    it.getValue(Car::class.java)?.copy(id = it.key ?: "")
                }
                adapter.submitList(cars)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Usertrain", "Database error: ${error.message}")
            }
        })
    }

    private fun getCurrentUserId() = FirebaseAuth.getInstance().currentUser?.uid ?: ""
}