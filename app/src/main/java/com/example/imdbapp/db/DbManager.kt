package com.example.imdbapp.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DbManager (context: Context) {
    val dbHelper = DbHelper(context)
    var dataBase: SQLiteDatabase ?= null

    fun openDb(){
        dataBase = dbHelper.writableDatabase
    }
    fun insertToDb(title: String){
        val values = ContentValues().apply {
            put(DbName.COLUMN_NAME_TITLE, title)

        }
        dataBase?.insert(DbName.TABLE_NAME, null, values)
    }
    @SuppressLint("Range")
    fun readDbData(): ArrayList<String>{
        val dataList = ArrayList<String>()

        val cursor = dataBase?.query(DbName.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null)
        with(cursor){
            while (this?.moveToNext()!!){
                val dataText = cursor?.getString(cursor.getColumnIndex(DbName.COLUMN_NAME_TITLE))
                dataList.add(dataText.toString())
            }
        }
        cursor?.close()
        return dataList

    }

    fun closeDb(){
        dbHelper.close()
    }
}