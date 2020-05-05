package com.asiantech.summer.recyclerview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.Items
import com.asiantech.summer.R
import com.asiantech.summer.data.User
import com.asiantech.summer.fragment.LoginFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-quynhho`.nav_header_to_do_main.view.*
import kotlinx.android.synthetic.`at-quynhho`.nav_menu_to_do_main.view.*

class MenuAdapter(
    private val listItem: ArrayList<Items>,
    private var user: User
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    var setOnItemClick: (position:Int) ->Unit? = {}
    companion object {
        private var NAV_HEADER = 0
        private var NAV_MENU = 1
        private val KEY = "items"
    }

    inner class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textMenu: TextView? = null
        var imgIcon: ImageView? = null

        init {
            textMenu = view.tvMenu
            imgIcon = view.imgMenu
            view.setOnClickListener{
              setOnItemClick.invoke(adapterPosition)
            }
        }

        fun bind() {
            Log.d("QQQ", "MenuHolder")
            listItem[adapterPosition].let {
                textMenu?.text = it.itemTitle
                imgIcon?.setImageResource(it.icon)
            }
        }

    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            Log.d("QQQ", "HeaderHolder")
            itemView.tvName.text = user.userName
            itemView.tvNick.text = user.nickName
            Glide.with(itemView)
                .load(user.avatar)
                .centerCrop()
                .placeholder(R.drawable.icons_user)
                .into(itemView.imageView)

        }

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
        Log.d("QQQ", "bind")
        (holder as? MenuViewHolder)?.bind()
        (holder as? HeaderViewHolder)?.bind()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            NAV_HEADER
        } else NAV_MENU
    }

    private fun newInstane(user: User): LoginFragment {
        val loginFragment = LoginFragment()
        loginFragment.arguments = Bundle().apply {
            putParcelable(KEY, getParcelable(user.toString()))
        }
        return loginFragment
    }
}