package com.example.xyeta2.user

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.xyeta2.R
import com.example.xyeta2.models.User
import com.example.xyeta2.utils.FirebaseHelper

class UserProfile : AppCompatActivity() {

    // Элементы UI
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvBalance: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        // Инициализация элементов UI
        tvName = findViewById(R.id.tvName)
        tvEmail = findViewById(R.id.tvEmail)
        tvBalance = findViewById(R.id.tvBalance)

        // Получение userId из Intent
        val userId = intent.getStringExtra("USER_ID") ?: run {
            Log.e("UserProfile", "USER_ID не передан в Intent")
            showErrorAndFinish("Ошибка: пользователь не выбран")
            return
        }

        Log.d("UserProfile", "Начало загрузки данных для userId: $userId")

        // Загрузка данных пользователя
        FirebaseHelper.getUserById(userId) { user ->
            if (user == null) {
                Log.e("UserProfile", "Данные пользователя $userId не найдены в Firebase")
                showErrorAndFinish("Данные пользователя недоступны")
                return@getUserById
            }

            Log.i("UserProfile", """
                Успешно загружены данные:
                Имя: ${user.name}
                Email: ${user.email}
                Баланс: ${user.balance}
            """.trimIndent())

            runOnUiThread {
                // Обновление UI
                tvName.text = user.name.ifEmpty { "Не указано" }
                tvEmail.text = user.email.ifEmpty { "Не указан" }
                tvBalance.text = "Баланс: ${user.balance} ₽"
            }
        }
    }

    private fun showErrorAndFinish(message: String) {
        runOnUiThread {
            Toast.makeText(
                this,
                message,
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
    }
}