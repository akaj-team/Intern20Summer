package com.asiantech.summer.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.ServiceCompat
import com.asiantech.summer.CreateNotification
import com.asiantech.summer.DataMedia
import com.asiantech.summer.Music
import com.asiantech.summer.RecyclerViewAdapter

@SuppressLint("Registered")
class MediaMusicService : Service(), MediaPlayer.OnCompletionListener {

    private var position = 0
    var mediaPlayer: MediaPlayer? = MediaPlayer()
    private var isPlaying = false
    private val binder = LocalBinder()

    companion object {
        var isShare = false
        var isRepeat = false
    }

    override fun onBind(intent: Intent): IBinder? {
        val path =intent.getStringExtra("MEDIA_PATH")
//        binder.playNextMedia(Uri.parse("MEDIA_PATH"))
        startForeground(0, createNotification(position))
        playMedia(Uri.parse(path))
 //       addAction()
        return binder
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        stopSelf()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (!isRepeat) {
            initNextMusic()
            mp?.isLooping = true
            mp?.start()
        } else mp?.stop()
        createNotification(position)
    }

    inner class LocalBinder : Binder() {
        fun getService(): MediaMusicService = this@MediaMusicService

        fun playNextMedia(uri: Uri) {
            getService().playMedia(uri)
        }
    }

    fun initNextMusic() {
        position++
 //       playMedia(Uri.parse())
    }

    fun initPreviousMusic() {
        position--
//        if (position < 0) {
//            position
//        }
//        playMedia(Uri.parse("MEDIA_PATH"))
    }

    fun initPlayPause() {
        if (mediaPlayer?.isPlaying != false) {
            mediaPlayer?.pause()
        } else {
            mediaPlayer?.start()
        }
        mediaPlayer?.start()
    }

    fun seekTo(current: Int) {
        mediaPlayer?.seekTo(current)
    }

    fun currentPosition() = mediaPlayer?.currentPosition

    fun initPosition(): Int = position

    fun isPlaying(): Boolean? = mediaPlayer?.isPlaying

    fun isRepeat(): Boolean = isRepeat

    fun isShare(): Boolean = isShare

    private fun playMedia(uri: Uri) {
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer?.apply {
            setOnCompletionListener(this@MediaMusicService)
            setDataSource(this@MediaMusicService, uri)
            prepare()
            setOnPreparedListener { start() }
        }
    }

    private fun createNotification(position: Int): Notification {
        val createNotification = CreateNotification(this)
        val media: DataMedia? = null
        val notification =
            binder.let { createNotification.createNotificationMusic(media!!, isPlaying) }
        startForeground(position, notification)
//        playMedia(Uri.parse("MEDIA_PATH"))
        isPlaying = this.isPlaying() ?: true
        return notification
    }

    private fun addAction() {
        val filter = IntentFilter()
        filter.apply {
            addAction(Music.ACTION_PAUSE)
            addAction(Music.ACTION_SKIP_NEXT)
            addAction(Music.ACTION_PREVIOUS)
            addAction(Music.ACTION_KILL_MEDIA)
        }
    }
}
