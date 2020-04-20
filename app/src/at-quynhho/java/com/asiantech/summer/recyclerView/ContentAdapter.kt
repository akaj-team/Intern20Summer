package com.asiantech.summer.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.R
import com.asiantech.summer.data.App
import com.asiantech.summer.data.DataMessage
import com.asiantech.summer.data.DateUtils
import kotlinx.android.synthetic.`at-quynhho`.item_my_conversation.view.*
import kotlinx.android.synthetic.`at-quynhho`.item_person_conversation.view.*
import kotlinx.android.synthetic.`at-quynhho`.time_converstation.view.*


class ContentAdapter(private val messages: ArrayList<DataMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private val VIEW_TYPE_MY_MESSAGE = 1
    private val VIEW_TYPE_OTHER_MESSAGE = 2

    inner class MyMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var messageText: TextView = view.tvMyConversation
        private var timeText: TextView = view.tvOtherMessageTime

        fun bind(message: DataMessage) {
            messageText.text = message.message
            timeText.text = DateUtils.fromMillisToTimeString(message.time)
        }
    }

    inner class OtherMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var messageText: TextView = view.tvPersonConversation
        //        private var userText: TextView = view.txtOtherUser
        private var timeText: TextView = view.tvOtherMessageTime

        fun bind(message: DataMessage) {
            messageText.text = message.message
            //           userText.text = message.user
            timeText.text = DateUtils.fromMillisToTimeString(message.time)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages.get(position)
        return if (App.user == message.user) {
            VIEW_TYPE_MY_MESSAGE
        } else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_MY_MESSAGE) {
            MyMessageViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_my_conversation,
                    parent,
                    false
                )
            )
        } else {
            OtherMessageViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_person_conversation,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MyMessageViewHolder)?.bind(messages[position])
        (holder as? OtherMessageViewHolder)?.bind(messages[position])
    }

}

