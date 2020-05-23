package com.asiantech.summer

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-quynhho`.item_media.view.*

class RecyclerViewAdapter(private var listMusic: ArrayList<DataMedia>) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder?>() {
//    var test :() ->Unit ={}

    internal var onPlayClick: (position: Int) -> Unit = {}

    companion object {
        const val MUSIC_LIST = "musiclist"
        const val MUSIC_POSITION = "music_postion"
    }

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

        val nameSong = itemView.tvNameMusicCard
        val nameSinger = itemView.tvNameSingCard
        val imgPlay = itemView.imgPlayMusicCard
        val timeSing = itemView.tvTimeCard

        init {
            itemView.setOnClickListener {
                onPlayClick.invoke(adapterPosition)
            }
        }

        fun bind() {
            listMusic[adapterPosition].let {
                nameSong.text = it.musicName
                nameSinger.text = it.musicArtist
                timeSing.text = Music.convertTimeMusic(it.timePlay)
                Glide.with(itemView)
                    .load(Uri.parse(it.imageMusic))
                    .placeholder(R.drawable.ic_music_30)
                    .into(imgPlay)
            }
        }
    }
}

