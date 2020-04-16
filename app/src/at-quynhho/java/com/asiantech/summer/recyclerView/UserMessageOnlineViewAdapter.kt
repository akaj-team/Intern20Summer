package com.asiantech.summer.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-quynhho`.user_online_horizontal_message.view.*
import java.util.*


class UserMessageOnlineViewAdapter : RecyclerView.Adapter<UserMessageOnlineViewAdapter.RecyclerViewHolder?>() {
    private var items: List<UserOnlineView> = ArrayList()

    class RecyclerViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val picUserOn = itemView.civPicUserOnline
        val userNamePersionOn = itemView.tvUserNameOnline
        fun bind(userOnlineView: UserOnlineView) {
            userNamePersionOn.text = userOnlineView.userNameOn
            Glide.with(itemView.context)
                .load(userOnlineView.pictureUserOn)
                .into(picUserOn)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_online_horizontal_message, parent, false)
        )
    }

    fun submitList(userOnlineList : List<UserOnlineView>){
        items = userOnlineList
    }
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        when (holder) {
            is RecyclerViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

}

