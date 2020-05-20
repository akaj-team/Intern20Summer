package com.asiantech.summer.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.asiantech.summer.CreateNotification
import com.asiantech.summer.DataMedia
import com.asiantech.summer.Music
import com.asiantech.summer.RecyclerViewAdapter

@Suppress("DEPRECATION")
@SuppressLint("Registered")
class MediaMusicService : Service(), MediaPlayer.OnCompletionListener {
    private var position = 0
    private var mediaMusicService: MediaMusicService? = null
    private var dataMedia: ArrayList<DataMedia> = ArrayList()
    private var mediaPlayer: MediaPlayer? = MediaPlayer()
    private var isPlaying = false
    private var listPath: ArrayList<String> = ArrayList()
    private var createNotification: CreateNotification? = null
    private val binder = LocalBinder()

    companion object {
        var isShare = false
        var isRepeat = false
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val mIntent: Intent = Intent(this, MediaMusicService::class.java)
//        mIntent.apply {
        if (position != null && dataMedia != null && listPath != null) {
            if (intent != null) {
                position = intent.getIntExtra(RecyclerViewAdapter.MUSIC_POSITION, 0)
                dataMedia = intent.getParcelableArrayListExtra(RecyclerViewAdapter.MUSIC_LIST)
                listPath = intent.getStringArrayListExtra(RecyclerViewAdapter.MUSIC_LIST)
            }

        }

//
//        }
//        createNotification(position)
//        playMedia(position)
        addAction()
        return START_NOT_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        stopSelf()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (!isRepeat) {
            initNextMusic()
        } else playMedia(position)
        createNotification(position)
    }

    inner class LocalBinder : Binder() {
        fun getService(): MediaMusicService = this@MediaMusicService
    }

    fun initNextMusic() {
        position++
        if (position > dataMedia.size - 1) {
            position = 0
        }
        playMedia(position)
    }

    fun initPreviousMusic() {
        position--
        if (position < 0) {
            position = dataMedia.size - 1
        }
        playMedia(position)
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

    private fun playMedia(mPosition: Int) {
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer?.apply {
            setOnCompletionListener(this@MediaMusicService)
            Log.d("BBB", "data: $dataMedia")
            setDataSource(dataMedia[mPosition].path)
            prepare()
            setOnPreparedListener { start() }
        }
    }

    fun createNotification(position: Int) {
        createNotification = CreateNotification(mediaMusicService!!)
        val notification =
            createNotification?.createNotificationMusic(dataMedia[position], isPlaying)
        startForeground(1, notification)
        isPlaying = this.isPlaying() ?: true
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Music.ACTION_PREVIOUS -> {
                    initPreviousMusic()
                    createNotification(position)
                }
                Music.ACTION_PAUSE -> {
                    initPlayPause()
                    createNotification(position)
                }
                Music.ACTION_SKIP_NEXT -> {
                    initNextMusic()
                    createNotification(position)
                }
                Music.ACTION_KILL_MEDIA -> {
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    stopForeground(true)
                }
            }
        }
    }

    private fun addAction() {
        val filter = IntentFilter()
        filter.apply {
            addAction(Music.ACTION_PAUSE)
            addAction(Music.ACTION_SKIP_NEXT)
            addAction(Music.ACTION_PREVIOUS)
            addAction(Music.ACTION_KILL_MEDIA)
        }
        registerReceiver(broadcastReceiver, filter)
    }
}
