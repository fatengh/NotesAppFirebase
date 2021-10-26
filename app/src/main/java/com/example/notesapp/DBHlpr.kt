package com.example.notesapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.util.Currency
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log

class DBHlpr(context: Context?) : SQLiteOpenHelper(context, "notes3.db", null, 1) {

    var sqlDB: SQLiteDatabase = writableDatabase // create database variable
    val sqlDBRead: SQLiteDatabase = readableDatabase


    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) { // cteate table
            db.execSQL("create table Note(Id INTEGER PRIMARY KEY AUTOINCREMENT ,Note text )")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }


    fun saveData(s1: String): Long { // to return : Long
        // add data to ContentValues
        val cv = ContentValues()
        cv.put("Note", s1)
        // add to database 3 para (name of table , null , contentValuse)
        var s = sqlDB.insert("Note", null, cv)
        return s
        // to check data saved or not
        // var statuse= sqlDB.insert("student",null , cv)
        // return statuse
        // return -1 not saved
    }

    fun retrive(): ArrayList<Note> {
        val list = ArrayList<Note>()
        val c: Cursor = sqlDBRead.query("Note", null, null, null, null, null, null)
        if (c.moveToFirst()) {
            var id = c.getInt(c.getColumnIndex("Id"))
            var note = c.getString(c.getColumnIndex("Note"))
            list.add(Note(id, note))
            while (c.moveToNext()) {
                id = c.getInt(c.getColumnIndex("Id"))
                note = c.getString(c.getColumnIndex("Note"))
                list.add(Note(id, note))
            }

        }
        c.close()
        return list
    }

    fun deleteNot(id: Int) {
        sqlDB.delete("Note","Id=?", arrayOf(id.toString()))

    }
    fun updateNot(id:Int, newNot:String){
        val cv=ContentValues()
        cv.put("Note",newNot)
        sqlDB.update("Note",cv,"Id=?", arrayOf(id.toString()))
    }
}



