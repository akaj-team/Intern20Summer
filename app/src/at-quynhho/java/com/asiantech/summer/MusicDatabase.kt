package com.asiantech.summer

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MusicDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VESION) {
//, factory: SQLiteDatabase.CursorFactory?

    companion object {
        private var TAG = ""
        private var DATABASE_VESION = 1
        private var DATABASE_NAME = "music.db"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME_SONG = "namesong"
        const val COLUMN_NAME_SINGER = "namesing"
        const val COLUMN_TIME = "time"
        const val TABLE_NAME = "datasong"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_PROVIDE_TABLE =
            ("CREATE TABLE" + TABLE_NAME + "(" + COLUMN_ID + "INTEGER PRIMARY KEY, " + COLUMN_NAME_SONG + "TEXT, " + COLUMN_NAME_SINGER + "TEXT," + COLUMN_TIME + "TIME" + ")")
        db?.execSQL(CREATE_PROVIDE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME)
    }

    fun getMusic(mName: DataSong) {
        val db = this.writableDatabase
        val value = ContentValues()
        value.put(COLUMN_NAME_SONG, mName.nameSong)
        value.put(COLUMN_NAME_SINGER, mName.nameSinger)
        value.put(COLUMN_TIME, mName.timePlay.time)
        db.insert(TABLE_NAME, null, value)
        db.close()
    }

    fun getAllMusic(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

}