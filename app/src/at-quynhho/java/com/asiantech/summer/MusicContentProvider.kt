package com.asiantech.summer

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

@SuppressLint("Registered")
class MusicContentProvider : ContentProvider() {

    private var mDB: MusicDatabase? = null
    private lateinit var mUriMatcher: UriMatcher

    companion object {
        private var AUTHORITY = "com.asiantech.summer.MusicContentProvider"
        private var MUSIC = 1
        private var MUSIC_ID = 2
        private var PROVIDE_MUSIC_TABLE = "musics"
        private var CONTENT_URI: Uri =
            Uri.parse("content://$AUTHORITY/$PROVIDE_MUSIC_TABLE")
        private var CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/at-music"
        private var CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/at-music"
    }

    init {
        mUriMatcher.addURI(AUTHORITY, PROVIDE_MUSIC_TABLE, MUSIC)
        mUriMatcher.addURI(AUTHORITY, "$PROVIDE_MUSIC_TABLE#", MUSIC_ID)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val uriType = mUriMatcher.match(uri)
        val sqlDB = mDB?.writableDatabase
        val mId: Long
        when (uriType) {
            MUSIC -> {
                mId = sqlDB?.insert(MusicDatabase.TABLE_NAME, null, values)!!
            }
            else ->{}
        }
        context.contentResolver.notifyChange(uri, null)
        return Uri.parse("$PROVIDE_MUSIC_TABLE/$mId")
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {

    }

    override fun onCreate(): Boolean {
        context?.let {
            mDB = MusicDatabase(it)
        }
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {

    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {

    }

    override fun getType(uri: Uri): String? {

    }
}