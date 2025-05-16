package com.example.xyeta2.Admin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.xyeta2.R
import com.google.firebase.firestore.FirebaseFirestore

class EditUserActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        // Инициализация Firestore
        db = FirebaseFirestore.getInstance()

        // Получаем ID пользователя из Intent
        userId = intent.getStringExtra("USER_ID") ?: ""

        // Находим элементы интерфейса
        val nameInput = findViewById<EditText>(R.id.et_name)
        val saveButton = findViewById<Button>(R.id.btn_save)

        // Загружаем текущие данные
        loadUserData(nameInput)

        // Обработчик кнопки сохранения
        saveButton.setOnClickListener {
            val newName = nameInput.text.toString().trim()
            if (newName.isNotEmpty()) {
                updateUser(newName)
            } else {
                Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadUserData(nameInput: EditText) {
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                nameInput.setText(document.getString("name"))
            }
    }

    // Функция обновления данных
    private fun updateUser(newName: String) {
        db.collection("users")
            .document(userId)
            .update("name", newName)
            .addOnSuccessListener {
                Toast.makeText(this, "Данные обновлены", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}