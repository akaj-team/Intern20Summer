package com.asiantech.summer.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.R
import com.asiantech.summer.data.UserOnline
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-quynhho`.item_user_online.view.*
import java.util.*


class UserOnlineAdapter : RecyclerView.Adapter<UserOnlineAdapter.RecyclerViewHolder?>() {
    private var items: List<UserOnline> = ArrayList()

    class RecyclerViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val picUserOn = itemView.civPicUserOnline
        val userNamePersionOn = itemView.tvUserNameOnline
        fun bind(userOnline: UserOnline) {
            userNamePersionOn.text = userOnline.userNameOn
            Glide.with(itemView.context)
                .load(userOnline.pictureUserOn)
                .into(picUserOn)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_online, parent, false)
        )
    }

    fun submitList(list: List<UserOnline>) {
        items = list
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.bind(items[position])
    }

}

