package ru.bsuir.feedthecat.service

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "feed_the_cat"
const val TABLE_NAME = "statistics"
const val COL_ID = "id"
const val COL_SATIETY = "satiety"
const val COL_TIME = "time"

class DatabaseHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_SATIETY + " VARCHAR(256), " +
                COL_TIME + " VARCHAR(64))";
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}