package com.asiantech.summer.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.R
import com.asiantech.summer.data.User
import kotlinx.android.synthetic.`at-quynhho`.item_list_user_message.view.*

class SearchAdapter(private val searches: ArrayList<User>) :
    RecyclerView.Adapter<SearchAdapter.RecyclerViewListHolder?>() {

    internal var userListClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewListHolder {
        return RecyclerViewListHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_user_message,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = searches.size

    override fun onBindViewHolder(holder: RecyclerViewListHolder, position: Int) {
        (holder as? RecyclerViewListHolder)?.bind(searches)
    }

    inner class RecyclerViewListHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val pictureUser = itemView.civAvatarUser
        val userName = itemView.tvUserName
        fun bind(searches: ArrayList<User>) {
            pictureUser.setImageResource(searches[adapterPosition].pictureUser)
            userName.text = searches[adapterPosition].userName
        }
    }

}
