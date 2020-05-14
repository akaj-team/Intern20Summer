package com.asiantech.summer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.`at-quynhho`.item_media.view.*

class RecyclerViewAdapter(private var listMusic: MutableList<DataSong>) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder?>() {

    internal var onPlayClick: (position: Int) -> Unit = {}
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_media,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listMusic.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.bind()
    }

    inner class RecyclerViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameSong = itemView.tvNameMusic
        val nameSinger  = itemView.tvNameSing
        val imgPlay = itemView.imgPlayMusic
        val timeSing = itemView.tvTime
        init {
            itemView.setOnClickListener {
                onPlayClick.invoke(adapterPosition)
            }
        }
        fun bind(){
            listMusic[adapterPosition].let {
                nameSong.text = it.nameSong
                nameSinger.text = it.nameSinger
                timeSing.text = it.timePlay.toString()
                it.buttonPlay.let {
                    imgPlay.setImageResource(R.drawable.ic_circledpause)
                }
            }
        }
    }
}

