package com.example.notesapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val mainViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }
    private lateinit var rv: RecyclerView
    private lateinit var edNote: EditText
    private lateinit var btnSub: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rv)

        mainViewModel.getNote().observe(this,{
                notes->
            rv.adapter = MyAdap(this, notes)
            rv.layoutManager = LinearLayoutManager(this)
        })

        edNote = findViewById(R.id.edNote)
        btnSub = findViewById(R.id.btnSub)



        btnSub.setOnClickListener {

            var s1 = edNote.text.toString()
            mainViewModel.addNote(s1)
            edNote.text.clear()
            mainViewModel.getNote().observe(this,{
                    notes->
                rv.adapter = MyAdap(this, notes)
                rv.layoutManager = LinearLayoutManager(this)
            })

        }
    }

    fun raiseDialog(note: Note) {
        val dialogBuilder = AlertDialog.Builder(this)
        val newNote = EditText(this)
        newNote.hint = "Enter new note"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                updateNote(note.id, newNote.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(newNote)
        alert.show()
    }

    fun updateNote(id: String, note: String) {
        mainViewModel.updateNote(id, note)
        mainViewModel.getNote().observe(this,{
                notes->
            rv.adapter = MyAdap(this, notes)
            rv.layoutManager = LinearLayoutManager(this)
        })

    }

    fun deleteNote(note: Note) {
        var id = note.id
        mainViewModel.delNote(id)
        mainViewModel.getNote().observe(this,{
                notes->
            rv.adapter = MyAdap(this, notes)
            rv.layoutManager = LinearLayoutManager(this)
        })

    }


}