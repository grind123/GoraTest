package com.grind.goratest.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grind.goratest.R
import com.grind.goratest.models.User

class UsersAdapter(private val listener: OnUserClickListener): RecyclerView.Adapter<UsersAdapter.UserHolder>() {

    val itemsList = mutableListOf<User>()


    class UserHolder(view: View): RecyclerView.ViewHolder(view){
        val userName: TextView = view.findViewById(R.id.tv_user_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val v = View.inflate(parent.context, R.layout.item_user, null).apply {
            layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        }
        return UserHolder(v)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val user = itemsList[position]
        holder.userName.text = user.name
        holder.itemView.setOnClickListener { listener.onUserClick(user) }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }


    interface OnUserClickListener{
        fun onUserClick(user: User)
    }
}