package com.grind.goratest.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlDbHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object{
        private const val DB_NAME = "PICTURE.db"
        private const val DB_VERSION = 1
        const val MAIN_TABLE_NAME = "pictures"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $MAIN_TABLE_NAME(" +
                    "_id INTEGER PRIMARY KEY," +
                    "title TEXT," +
                    "imageFilePath TEXT);)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // not need implemented
    }
}