package com.asiantech.summer.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.`at-quynhho`.user_vertical_message.view.*
import java.util.*

class TabBarInforUserMessageAdapter :
    RecyclerView.Adapter<TabBarInforUserMessageAdapter.RecyclerInfoViewHolder?>() {

    private var itemsInfo: List<InformationUserMessage> = ArrayList()

    class RecyclerInfoViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarUserMess = itemView.civAvatarUserMess
        val userNameUserChat = itemView.tvUserNameChat
        val inforMessUser = itemView.tvInformationChat
        var checkNumMess = itemView.tvCheckNumMess

        fun bindInfo(informationUserMessage: InformationUserMessage) {
            userNameUserChat.text = informationUserMessage.userNameUser
            inforMessUser.text = informationUserMessage.messageUser
            checkNumMess?.apply {
                text = informationUserMessage.checkNumber.toString()
                background.setBackgroundResource(R.drawable.ic_background_check_number)
            }



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

private fun Any.setBackgroundResource(icBackgroundCheckNumber: Int) {

}

