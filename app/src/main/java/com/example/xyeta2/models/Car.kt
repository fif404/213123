package com.example.xyeta2.models


// models/Car.kt
data class Car(
    val id: String = "",
    val model: String = "",
    val pricePerHour: Double = 0.0,
    val available: Boolean = true
)