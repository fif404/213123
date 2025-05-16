package com.example.xyeta2.Admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xyeta2.R
import com.example.xyeta2.models.User
import com.example.xyeta2.utils.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainAdmin : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var usersList: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_admin)

        // Инициализация RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Инициализация списка пользователей
        usersList = mutableListOf()

        // Загрузка данных из Firebase
        loadUsers()

        // Обработчики кнопок
        findViewById<Button>(R.id.button5).setOnClickListener {
            startActivity(Intent(this, Park::class.java))
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            startActivity(Intent(this, AllStat::class.java))
        }
    }

    private fun loadUsers() {
        FirebaseHelper.getUsers(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersList.clear()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    user?.let {
                        usersList.add(it.copy(id = userSnapshot.key ?: ""))
                    }
                }
                setupAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибок
                error.toException().printStackTrace()
            }
        })
    }

    private fun setupAdapter() {
        val adapter = UserAdapter(
            users = usersList,
            onProfileClick = { userId ->
                // Обработка просмотра профиля
            },
            onDeleteClick = { userId ->
                // Обработка удаления
            },
            onEditClick = { user ->
                // Обработка редактирования
            }
        )
        recyclerView.adapter = adapter
    }
}