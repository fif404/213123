package com.example.xyeta2.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.xyeta2.models.User // Добавьте этот импорт

object FirebaseHelper {
    // Инициализация Firebase компонентов
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val database: FirebaseDatabase by lazy { Firebase.database }

    // Ссылки на узлы базы данных
    private val usersRef: DatabaseReference get() = database.getReference("users")
    private val carsRef: DatabaseReference get() = database.getReference("cars")

    // Работа с пользователями
    fun getUsers(listener: ValueEventListener) {
        usersRef.addListenerForSingleValueEvent(listener)
    }

    fun deleteUser(userId: String) {
        usersRef.child(userId).removeValue()
    }

    fun updateUser(userId: String, updates: Map<String, Any>) {
        usersRef.child(userId).updateChildren(updates)
    }

    // Работа с автомобилями
    fun getCars(listener: ValueEventListener) {
        carsRef.addListenerForSingleValueEvent(listener)
    }

    fun rentCar(userId: String, carId: String) {
        val updates = mapOf(
            "cars/$carId/available" to false,
            "users/$userId/rentedCar" to carId
        )
        database.reference.updateChildren(updates)
    }

    // Аутентификация
    fun getCurrentUser() = auth.currentUser

    fun getUserById(userId: String, callback: (User?) -> Unit) {
        usersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot.getValue(User::class.java)) // Используем класс User
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }


    fun checkAdminStatus(callback: (Boolean) -> Unit) {
        auth.currentUser?.uid?.let { uid ->
            usersRef.child(uid).child("isAdmin")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        callback(snapshot.getValue(Boolean::class.java) ?: false)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        callback(false)
                    }
                })
        } ?: callback(false)
    }
}