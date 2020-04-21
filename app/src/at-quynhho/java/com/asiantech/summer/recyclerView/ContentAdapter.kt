package com.asiantech.summer.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.R
import com.asiantech.summer.data.DataMessage
import kotlinx.android.synthetic.`at-quynhho`.item_my_conversation.view.*
import kotlinx.android.synthetic.`at-quynhho`.item_person_conversation.view.*
import kotlinx.android.synthetic.`at-quynhho`.time_converstation.view.*
import java.text.SimpleDateFormat
import java.util.*

class ContentAdapter(private val message: MutableList<DataMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    companion object {
        private const val VIEW_TYPE_MY_MESSAGE = 1
        private const val VIEW_TYPE_OTHER_MESSAGE = 2
        private const val TIME_MESSAGE = 3
    }

    inner class TimeMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var timeText: TextView = view.tvOtherMessageTime
        fun bind(message: MutableList<DataMessage>) {
            val currentTime: String =
                SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            timeText.text = currentTime
        }
    }

    inner class MyMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var messageText: TextView = view.tvMyConversation

        fun bind(message: MutableList<DataMessage>) {
            messageText.text = message[adapterPosition].message
        }
    }

    inner class OtherMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var messageText: TextView = view.tvPersonConversation

        fun bind(message: MutableList<DataMessage>) {
            messageText.text = message[adapterPosition].message
        }
    }

    override fun getItemCount() = message.size + 1

    override fun getItemViewType(position: Int): Int {
        if (position == message.size) {
            return TIME_MESSAGE
        } else if (message[position].isMyMessage == true) {
            return VIEW_TYPE_MY_MESSAGE
        } else {
            return VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TIME_MESSAGE) {
            TimeMessageViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.time_converstation,
                    parent,
                    false
                )
            )
        } else if (viewType == VIEW_TYPE_MY_MESSAGE) {
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
        (holder as? MyMessageViewHolder)?.bind(message)
        (holder as? OtherMessageViewHolder)?.bind(message)
        (holder as? TimeMessageViewHolder)?.bind(message)
    }

}

