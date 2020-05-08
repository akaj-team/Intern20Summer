package com.asiantech.summer.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.Items
import com.asiantech.summer.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-quynhho`.nav_header_to_do_main.view.*
import kotlinx.android.synthetic.`at-quynhho`.nav_menu_to_do_main.view.*

class MenuAdapter(
    private val listItem: ArrayList<Items>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    var setOnItemClick: (position: Int) -> Unit? = {}

    companion object {
        private var NAV_HEADER = 0
        private var NAV_MENU = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == NAV_HEADER) {
            HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.nav_header_to_do_main,
                    parent,
                    false
                )
            )
        } else {
            MenuViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.nav_menu_to_do_main,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MenuViewHolder)?.bind()
        (holder as? HeaderViewHolder)?.bind()
    }

    override fun getItemViewType(position: Int): Int {
        return if (listItem[position].user != null) {
            NAV_HEADER
        } else NAV_MENU
    }

    inner class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textMenu: TextView? = null
        var imgIcon: ImageView? = null

        init {
            textMenu = view.tvMenu
            imgIcon = view.imgMenu
            view.setOnClickListener {
                setOnItemClick.invoke(adapterPosition)
            }
        }

        fun bind() {
            listItem[adapterPosition].let {
                textMenu?.text = it.itemTitle
                it.icon?.let {
                    imgIcon?.setImageResource(it)
                }
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            listItem[adapterPosition].user?.let {
                itemView.tvName.text = it.userName
                itemView.tvNick.text = it.nickName
                Glide.with(itemView)
                    .load(it.avatar)
                    .centerCrop()
                    .placeholder(R.drawable.icons_user)
                    .into(itemView.civImageView)
            }
        }
    }
}