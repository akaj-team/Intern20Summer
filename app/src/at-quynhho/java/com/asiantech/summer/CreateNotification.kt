package com.asiantech.summer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.asiantech.summer.Music.ACTION_KILL_MEDIA
import com.asiantech.summer.Music.ACTION_PAUSE
import com.asiantech.summer.Music.ACTION_PREVIOUS
import com.asiantech.summer.Music.ACTION_SKIP_NEXT
import com.asiantech.summer.service.MediaMusicService

class CreateNotification(private val context: Context) {
    private var session: MediaSessionCompat? = null
    private var manager: NotificationManager = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        private const val REQUEST_CODE = 100
        const val CHANNEL_ID = "channelid"
    }

    fun createNotificationMusic(music: DataMedia, isPlaying: Boolean): Notification {
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
        createNotificationChannel()
        val intentActivity = Intent(context, PlayerMusicActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT
        )
        //create
        notificationBuilder.apply {
            setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(session?.sessionToken)
                    .setShowActionsInCompactView(0, 1, 2, 3)
            )
            setContentTitle(music.musicName)
            setSmallIcon(R.drawable.ic_music_30)
            setLargeIcon(Music.songPicture(Uri.parse(music.path), context))
            setContentText(music.musicArtist)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
            setOnlyAlertOnce(true)
            setOngoing(true)
            addAction(notificationAction(ACTION_PREVIOUS, isPlaying))
            addAction(notificationAction(ACTION_PAUSE, isPlaying))
            addAction(notificationAction(ACTION_SKIP_NEXT, isPlaying))
            addAction(notificationAction(ACTION_KILL_MEDIA, isPlaying))
        }
        return notificationBuilder.build()

    }

    private fun notificationAction(action: String, isPlaying: Boolean): NotificationCompat.Action? {
        val icon: Int = when (action) {
            ACTION_PREVIOUS -> R.drawable.ic_skip_to_start_30
            ACTION_PAUSE -> if (isPlaying) {
                R.drawable.ic_play_arrow_black_30
            } else {
                R.drawable.ic_pause_30
            }
            ACTION_SKIP_NEXT -> R.drawable.ic_skip_next_30
            ACTION_KILL_MEDIA -> R.drawable.ic_play_arrow_black_30
            else -> R.drawable.ic_skip_next_30
        }
        return NotificationCompat.Action.Builder(icon, action, intentAction(action)).build()
    }

    private fun createNotificationChannel() {
        if (SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }
    private fun intentAction(action: String): PendingIntent {
        val broadCastIntent = Intent()
        broadCastIntent.action = action
        return PendingIntent.getBroadcast(context, REQUEST_CODE, broadCastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}
