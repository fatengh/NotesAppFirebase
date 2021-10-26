package com.example.notesapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Note")
data class Note(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id") val id : Int = 0,
    @ColumnInfo(name = "Note") val note:String?=""
)
