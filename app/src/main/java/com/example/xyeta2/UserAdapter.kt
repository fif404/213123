// UserAdapter.kt
package com.example.xyeta2.Admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xyeta2.R
import com.example.xyeta2.models.User

class UserAdapter(
    private val users: List<User>,
    private val onProfileClick: (String) -> Unit,
    private val onDeleteClick: (String) -> Unit,
    private val onEditClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.apply {
                findViewById<TextView>(R.id.tvName).text = user.name
                findViewById<TextView>(R.id.tvEmail).text = user.email
                findViewById<TextView>(R.id.tvBalance).text = "Баланс: ${user.balance} ₽"

                findViewById<Button>(R.id.btnProfile).setOnClickListener {
                    onProfileClick(user.id)
                }

                findViewById<Button>(R.id.btnDelete).setOnClickListener {
                    onDeleteClick(user.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size
}