package com.example.notesapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val mainViewModel by lazy {
        ViewModelProvider(this).get(MyViewModel::class.java)
    }
    private lateinit var rv: RecyclerView
    private lateinit var edNote: EditText
    private lateinit var btnSub: Button
    private lateinit var notes: List<Note>
   // lateinit var noteDB : NoteDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rv)

        mainViewModel.getNote().observe(this){
            rv.adapter = MyAdap(this, it)
            rv.layoutManager = LinearLayoutManager(this)
        }
        //noteDB = NoteDatabase.getInstance(applicationContext)
        edNote = findViewById(R.id.edNote)
        btnSub = findViewById(R.id.btnSub)

        // get data and put it into rv
        //notes = noteDB.NoteDoa().getNotes()

        btnSub.setOnClickListener {

            var s1 = edNote.text.toString()
            if (s1.isNotEmpty()) {
                mainViewModel.addNote(Note(note = edNote.text.toString()))
                // noteDB.NoteDoa().insertNote(Note(note= edNote.text.toString()))
               //  notes = noteDB.NoteDoa().getNotes()
                mainViewModel.getNote().observe(this){
                rv.adapter = MyAdap(this, it)
                rv.layoutManager = LinearLayoutManager(this)}
                Toast.makeText(applicationContext, "notes added ", Toast.LENGTH_LONG).show()
                edNote.text.clear()
            } else {
                Toast.makeText(applicationContext, "please enter anything", Toast.LENGTH_LONG)
                    .show()

            }

        }
    }

    fun raiseDialog(note: Note) {
        val dialogBuilder = AlertDialog.Builder(this)
        val newNote = EditText(this)
        newNote.hint = "Enter new note"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                updateNote(Note(note.id, newNote.text.toString()))
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(newNote)
        alert.show()
    }
    fun updateNote(note:Note){
       // noteDB.NoteDoa().updateNote(note)
       // notes = noteDB.NoteDoa().getNotes()
        mainViewModel.updateNote(note)
        mainViewModel.getNote().observe(this) {
            rv.adapter = MyAdap(this, it)
            rv.layoutManager = LinearLayoutManager(this)
        }
    }

    fun deleteNote(note:Note) {
       // noteDB.NoteDoa().deleteNote(note)
       // notes = noteDB.NoteDoa().getNotes()
        mainViewModel.delNote(note)
        mainViewModel.getNote().observe(this){
        rv.adapter = MyAdap(this, it)
        rv.layoutManager = LinearLayoutManager(this)
    }
}}