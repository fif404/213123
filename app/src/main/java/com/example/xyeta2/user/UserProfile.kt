package com.example.xyeta2.user

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.xyeta2.R
import com.example.xyeta2.models.User  // Правильный импорт
import com.example.xyeta2.utils.FirebaseHelper

class UserProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val userId = intent.getStringExtra("USER_ID") ?: return

        val tvName: TextView = findViewById(R.id.tvName)
        val tvEmail: TextView = findViewById(R.id.tvEmail)
        val tvBalance: TextView = findViewById(R.id.tvBalance)

        FirebaseHelper.getUserById(userId) { user ->
            user?.let {
                runOnUiThread {
                    tvName.text = it.name
                    tvEmail.text = it.email
                    tvBalance.text = "Баланс: ${it.balance} ₽"
                }
            }
        }
    }
}