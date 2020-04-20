package com.asiantech.summer.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.R
import com.asiantech.summer.data.Conversation
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-quynhho`.item_conversation.view.*

class ConversationAdapter(private var itemsInfo: List<Conversation>) :
    RecyclerView.Adapter<ConversationAdapter.RecyclerInfoViewHolder?>() {
    internal var userChatClicked: (position: Int) -> Unit = {}
    internal var onStartClick: (position: Int) -> Unit = {}

    inner class RecyclerInfoViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val avatarUserMess = itemView.civAvatarUserMess
        val userNameUserChat = itemView.tvUserNameChat
        val inforMessUser = itemView.tvInformationChat
        var tvMessageUnread = itemView.tvMessageUnread
        var imgStar = itemView.imgStarOn

        init {
            itemView.setOnClickListener {
                userChatClicked.invoke(adapterPosition)
            }
            imgStar.setOnClickListener {
                onStartClick.invoke(adapterPosition)
            }
        }

        fun bindInfo(conversation: Conversation) {
            userNameUserChat.text = conversation.userNameUser
            inforMessUser.text = conversation.messageUser
            if (conversation.numberMessageUnRead == 0) {
                tvMessageUnread.visibility = View.GONE
            } else {
                tvMessageUnread.text = conversation.numberMessageUnRead.toString()
                tvMessageUnread.visibility = View.VISIBLE
            }
            imgStar.isSelected = conversation.isLike

            Glide.with(itemView.context)
                .load(conversation.avatarUser)
                .into(avatarUserMess)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerInfoViewHolder {


        return RecyclerInfoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_conversation,
                parent,
                false
            )
        )
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

