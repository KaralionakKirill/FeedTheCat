package ru.bsuir.feedthecat.service

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import ru.bsuir.feedthecat.model.Score
import ru.bsuir.feedthecat.model.Statistic

class DatabaseManager(var context: Context) {
    private val databaseHelper = DatabaseHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = databaseHelper.writableDatabase
    }

    fun insert(statistic: Statistic) {
        val cv = ContentValues().apply {
            put(COL_TIME, statistic.time)
            put(COL_SATIETY, statistic.satiety)
        }
        val result = db?.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }

    @SuppressWarnings("Range")
    fun read(): ArrayList<Score> {
        val dataList = ArrayList<Score>()
        val cursor = db?.query(TABLE_NAME, null,null,null,null,null,null)
        while (cursor?.moveToNext()!!) {
            val time = cursor.getString(cursor.getColumnIndex(COL_TIME))
            val satiety = cursor.getInt(cursor.getColumnIndex(COL_SATIETY))
            dataList.add(Score(satiety, time))
        }
        cursor.close()
        return dataList
    }

    fun close() {
        databaseHelper.close()
    }
}