package com.example.imdbapp.db

import android.provider.BaseColumns

object DbName: BaseColumns {
    const val TABLE_NAME = "history"
    const val COLUMN_NAME_TITLE = "title"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "UserHistory.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ("+
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_TITLE TEXT)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

}