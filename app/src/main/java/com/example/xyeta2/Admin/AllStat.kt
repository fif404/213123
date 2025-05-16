// AllStat.kt
package com.example.xyeta2.Admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xyeta2.databinding.ActivityAllStatBinding
import com.example.xyeta2.models.User
import com.example.xyeta2.utils.FirebaseHelper
import com.example.xyeta2.user.UserProfile // Добавляем импорт
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class AllStat : AppCompatActivity() {

    private lateinit var binding: ActivityAllStatBinding
    private lateinit var adapter: UserAdapter
    private val usersList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllStatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadStatistics()
        setupButtons()
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(
            usersList,
            onProfileClick = { userId: String -> openUserProfile(userId) }, // Явно указываем тип
            onDeleteClick = { userId: String -> deleteUser(userId) },
            onEditClick = { user: User -> editUser(user) }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@AllStat)
            this.adapter = this@AllStat.adapter
        }
    }

    private fun loadStatistics() {
        FirebaseHelper.getUsers(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersList.clear()
                snapshot.children.forEach { data ->
                    data.getValue(User::class.java)?.let { usersList.add(it) }
                }

                val totalBalance = usersList.sumOf { it.balance }
                val totalTrips = usersList.flatMap { it.trips.values }.size

                runOnUiThread {
                    adapter.notifyDataSetChanged()
                    binding.totalBalance.text = "Общий баланс: ${totalBalance} ₽"
                    binding.totalTrips.text = "Всего поездок: $totalTrips"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("AllStat", "Database error: ${error.message}")
            }
        })
    }

    private fun deleteUser(userId: String) {
        FirebaseHelper.deleteUser(userId)
        loadStatistics()
    }

    private fun editUser(user: User) {
        val intent = Intent(this, EditUserActivity::class.java).apply {
            putExtra("USER_ID", user.id) // Убедитесь, что EditUserActivity существует
        }
        startActivity(intent)
    }

    private fun openUserProfile(userId: String) {
        val intent = Intent(this, UserProfile::class.java).apply {
            putExtra("USER_ID", userId) // Убедитесь, что UserProfileActivity существует
        }
        startActivity(intent)
    }

    private fun setupButtons() {
        binding.btnRefresh.setOnClickListener {
            loadStatistics()
        }
    }
}