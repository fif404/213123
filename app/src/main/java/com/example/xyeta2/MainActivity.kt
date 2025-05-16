package com.example.xyeta2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация элементов интерфейса
        emailInput = findViewById(R.id.email_et)
        loginButton = findViewById(R.id.login_btn)

        // Обработчик нажатия кнопки входа
        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()

            if (validateEmail(email)) {
                checkUserRole(email)
            } else {
                showError("Введите корректный email")
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkUserRole(email: String) {
        when (email) {
            "qwe@gmail.com" -> navigateToAdmin()
            "123@gmail.com" -> navigateToUser()
            else -> showError("Пользователь не найден")
        }
    }

    private fun navigateToAdmin() {
        startActivity(Intent(this, com.example.xyeta2.Admin.MainAdmin::class.java))
        finish() // Закрываем экран входа после перехода
    }

    private fun navigateToUser() {
        startActivity(Intent(this, com.example.xyeta2.user.MainUser::class.java))
        finish() // Закрываем экран входа после перехода
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}