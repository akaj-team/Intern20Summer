package com.asiantech.summer

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import java.util.concurrent.TimeUnit

object Music {
    private var dataMedia: ArrayList<DataMedia> = arrayListOf()
    const val ACTION_PAUSE = "playpause"
    const val ACTION_SKIP_NEXT = "skipnext"
    const val ACTION_PREVIOUS = "previous"
    const val ACTION_KILL_MEDIA = "killmedia"


    fun convertTimeMusic(millis: Int): String {
        return String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(millis.toLong()) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(
                    millis.toLong()
                )
            )
        )
    }

    fun songPicture(path: Uri, context: Context): Bitmap? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, path)
        val byteArray = retriever.embeddedPicture
        if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return null
    }


    fun insertData(context: Context): ArrayList<DataMedia> {
        dataMedia.clear()
        val musicCursor: Cursor? = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null, null,
            null
        )
        while (musicCursor != null && musicCursor.moveToNext()) {
            val musicName =
                musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val musicArtist =
                musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val musicTime =
                musicCursor.getInt(musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            val albumId: Long =
                musicCursor.getLong(musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
            val pictureUri: Uri = Uri.parse("content://media/external/audio/albumart")
            // musicCursor.getString(musicCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)).toString()
            val data =
                Uri.parse(musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA)))
            val albumArtUri: String = ContentUris.withAppendedId(pictureUri, albumId).toString()
            if (musicArtist!=null){
                dataMedia.add(
                    DataMedia(
                        data.toString(),
                        musicName,
                        musicArtist,
                        albumArtUri,
                        musicTime
                    )
                )
            }
        }
        musicCursor?.close()
        return dataMedia
    }
}