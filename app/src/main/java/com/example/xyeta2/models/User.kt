package com.example.xyeta2.models

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val balance: Double = 0.0,
    val trips: Map<String, Trip> = emptyMap()  // Добавлено поле trips
)