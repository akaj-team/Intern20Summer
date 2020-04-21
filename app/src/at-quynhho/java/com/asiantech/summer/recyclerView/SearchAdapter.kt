package com.asiantech.summer.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.R
import com.asiantech.summer.data.UserOnline
import kotlinx.android.synthetic.`at-quynhho`.item_list_user_message.view.*

class SearchAdapter(private val search: ArrayList<UserOnline>) :
    RecyclerView.Adapter<SearchAdapter.RecyclerViewListHolder?>() {

    internal var userListClicked: (position: Int) -> Unit = {}

    inner class RecyclerViewListHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val pictureUser = itemView.civAvatarUser
        val userName = itemView.tvUserName
        fun bind(search: ArrayList<UserOnline>) {
            pictureUser.setImageResource(search[adapterPosition].pictureUserOn)
            userName.text = search[adapterPosition].userNameOn
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewListHolder {
        return RecyclerViewListHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_user_message,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return search.size
    }

    override fun onBindViewHolder(holder: RecyclerViewListHolder, position: Int) {
        (holder as? RecyclerViewListHolder)?.bind(search)
    }

}
