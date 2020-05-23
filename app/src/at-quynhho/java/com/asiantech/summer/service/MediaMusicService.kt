package com.asiantech.summer.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import com.asiantech.summer.fragment.ListMediaFragment

class MediaMusicService : Service(), MediaPlayer.OnCompletionListener {
    companion object {
        var isShare = false
        var isRepeat = false
    }

    private var position = 0
    private var intent: Intent? = null
    var mediaPlayer: MediaPlayer? = MediaPlayer()
    private var isPlaying = false
    private val binder = LocalBinder()
    private val notification: Notification? = null

    override fun onBind(intent: Intent): IBinder? {
        intent.getStringExtra("MEDIA_PATH")
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        startForeground(position, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        stopSelf()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
        if (mediaPlayer!=null){
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
        return false

    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (isPlaying) {
            onPlayMusic()
        } else {
            onPlayMusic()
        }
    }

    inner class LocalBinder : Binder() {
        fun getService(): MediaMusicService = this@MediaMusicService

        fun playNextMedia(uri: Uri) {
            getService().playMedia(uri)
        }
    }

    fun onNextMusic() {
        ListMediaFragment.newInstance(isPlaying).nextMusic()
    }

    fun onPreviousMusic() {
        ListMediaFragment.newInstance(isPlaying).previousMusic()
    }

    fun onPauseMusic() {
        isPlaying = true
        mediaPlayer?.pause()
    }

    fun onPlayMusic() {
        isPlaying = false
        if (intent == null) {
        } else {
            mediaPlayer?.start()
        }
    }

    private fun playMedia(uri: Uri) {
        if (mediaPlayer!=null) {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.apply {
                setOnCompletionListener(this@MediaMusicService)
                setDataSource(this@MediaMusicService, uri)
                prepare()
                setOnPreparedListener {
                    start()
                }
            }
        }
    }

}
