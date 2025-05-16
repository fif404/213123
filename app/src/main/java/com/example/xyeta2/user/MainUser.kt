package com.example.xyeta2.user

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xyeta2.R
import android.content.Intent
import android.widget.Button
import com.example.xyeta2.Admin.AllStat
import com.example.xyeta2.Admin.Park

class MainUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user)

        findViewById<Button>(R.id.btnProfile).setOnClickListener {
            startActivity(Intent(this, UserProfile::class.java))
        }

        findViewById<Button>(R.id.btnTrips).setOnClickListener {
            startActivity(Intent(this, UsertrainActivity::class.java)) // Исправлено имя класса
        }

        findViewById<Button>(R.id.btnSupport).setOnClickListener {
            startActivity(Intent(this, UserSupport::class.java))
        }
    }
}