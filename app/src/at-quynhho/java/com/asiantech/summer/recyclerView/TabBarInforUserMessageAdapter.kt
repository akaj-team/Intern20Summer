package com.asiantech.summer.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-quynhho`.user_online_horizontal_message.view.*
import kotlinx.android.synthetic.`at-quynhho`.user_vertical_message.view.*
import java.util.ArrayList

class TabBarInforUserMessageAdapter :
    RecyclerView.Adapter<TabBarInforUserMessageAdapter.RecyclerInfoViewHolder?>() {

    private var itemsInfo: List<InformationUserMessage> = ArrayList()

    class RecyclerInfoViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarUserMess = itemView.civAvatarUserMess
        val userNameUserChat = itemView.tvUserNameChat
        val inforMessUser = itemView.tvInformationChat
        fun bindInfo(informationUserMessage: InformationUserMessage) {
            userNameUserChat.text = informationUserMessage.userNameUser
            inforMessUser.text = informationUserMessage.messageUser
            Glide.with(itemView.context)
                .load(informationUserMessage.avatarUser)
                .into(avatarUserMess)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerInfoViewHolder {
        return RecyclerInfoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.user_vertical_message,
                parent,
                false
            )
        )
    }

    fun submitInforMess(informationUserMessage: List<InformationUserMessage>) {
        itemsInfo = informationUserMessage
    }

    override fun getItemCount(): Int {
        return itemsInfo.size
    }

    override fun onBindViewHolder(holder: RecyclerInfoViewHolder, position: Int) {
        when (holder) {
            is RecyclerInfoViewHolder -> {
                holder.bindInfo(itemsInfo.get(position))
            }
        }
    }
}



