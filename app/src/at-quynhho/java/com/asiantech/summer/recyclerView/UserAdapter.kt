package com.asiantech.summer.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.R
import com.asiantech.summer.data.User
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-quynhho`.item_user_online.view.*
import java.util.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.RecyclerViewHolder?>() {
    private var items: List<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_online, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.bind(items[position])
    }

    inner class RecyclerViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val picUserOn = itemView.civPicUserOnline
        val userNamePersionOn = itemView.tvUserNameOnline
        fun bind(user: User) {
            userNamePersionOn.text = user.userName
            Glide.with(itemView.context)
                .load(user.pictureUser)
                .into(picUserOn)

        }
    }

    fun submitList(list: List<User>) {
        items = list
    }
}

