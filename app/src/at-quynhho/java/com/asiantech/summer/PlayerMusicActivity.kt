package com.asiantech.summer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asiantech.summer.service.MediaMusicService

class PlayerMusicActivity : AppCompatActivity() {

    private var recyclerViewAdapter:RecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_music)
 //       startActivity(Intent(MediaMusicService::class.java))
    }
}
