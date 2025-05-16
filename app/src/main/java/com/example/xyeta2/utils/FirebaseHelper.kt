package com.example.xyeta2.utils

import android.util.Log
import com.example.xyeta2.models.Car
import com.example.xyeta2.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object FirebaseHelper {
    // Инициализация Firebase компонентов (Realtime Database)
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val database: FirebaseDatabase by lazy { Firebase.database }

    // Ссылки на узлы базы данных
    private val usersRef: DatabaseReference get() = database.getReference("users")
    private val carsRef: DatabaseReference get() = database.getReference("cars")

    // ==================== Методы для работы с пользователями ====================
    fun getUserById(userId: String, callback: (User?) -> Unit) {
        usersRef.child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(User::class.java)
                        callback(user?.copy(id = userId))
                    } else {
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseHelper", "User load error: ${error.message}")
                    callback(null)
                }
            })
    }



    fun updateUser(userId: String, updates: Map<String, Any>) {
        usersRef.child(userId).updateChildren(updates)
    }

    // ==================== Методы для работы с автомобилями ====================
    fun getCars(listener: ValueEventListener) {
        carsRef.addValueEventListener(listener)
    }

    fun rentCar(userId: String, carId: String) {
        val updates = hashMapOf<String, Any>(
            "cars/$carId/available" to false,
            "users/$userId/rentedCar" to carId
        )
        database.reference.updateChildren(updates)
            .addOnSuccessListener {
                Log.d("FirebaseHelper", "Car rented successfully")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseHelper", "Rent error: ${e.message}")
            }
    }

    fun getUsers(listener: ValueEventListener) {
        usersRef.addValueEventListener(listener)
    }

    // Исправленный метод для удаления пользователя (Realtime Database)
    fun deleteUser(userId: String) {
        usersRef.child(userId)
            .removeValue()
            .addOnSuccessListener {
                Log.d("FirebaseHelper", "User $userId deleted")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseHelper", "Delete error: ${e.message}")
            }
    }

    // ==================== Общие методы ====================
    fun getCurrentUser() = auth.currentUser
}