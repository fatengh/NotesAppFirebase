package com.example.notesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.coroutineContext

class MyViewModel(applicationContext: Application) : AndroidViewModel(applicationContext) {
    private val noteDao = NoteDatabase.getInstance(applicationContext).NoteDoa()

    private val notes: LiveData<List<Note>>
    val applicationContext = applicationContext

    init {
        notes = noteDao.getNotes()
    }

    fun getNote(): LiveData<List<Note>> {
        return notes
    }

    fun addNote(note: Note) {
        noteDao.insertNote(note)
    }

        fun updateNote(note: Note) {
            noteDao.updateNote(note)
        }

        fun delNote(note: Note) {
            noteDao.deleteNote(note)
        }
    }