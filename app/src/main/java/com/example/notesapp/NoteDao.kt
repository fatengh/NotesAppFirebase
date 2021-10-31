package com.example.notesapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Query("select * from Note  order by Id ASC")
    fun getNotes(): LiveData<List<Note>>
    @Insert
    fun insertNote(note:Note)
    @Update
    fun updateNote(note: Note)
    @Delete
    fun deleteNote(note: Note)
}