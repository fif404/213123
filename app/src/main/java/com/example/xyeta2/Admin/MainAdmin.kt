package com.example.xyeta2.Admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xyeta2.R
import android.content.Intent
import android.widget.Button

class MainAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_admin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.button5).setOnClickListener {
            startActivity(Intent(this, Park::class.java))
        }

        // Обработчик для кнопки "статистика" (button4)
        findViewById<Button>(R.id.button4).setOnClickListener {
            startActivity(Intent(this, AllStat::class.java))
        }
    }
}