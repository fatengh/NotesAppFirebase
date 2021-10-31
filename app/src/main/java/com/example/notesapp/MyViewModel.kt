package com.example.notesapp

import android.app.Application

import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyViewModel(applicationContext: Application) : AndroidViewModel(applicationContext) {

    val db = Firebase.firestore

    private val notes: MutableLiveData<List<Note>> = MutableLiveData()
    val app = applicationContext





    fun getNote():LiveData<List<Note>>{
        db.collection("notes")
            .get()
            .addOnSuccessListener {
                val al=ArrayList<Note>()
                for (note in it){
                    note.data.map {
                            (k,v)->
                        al.add(Note(note.id,v.toString()))
                    }
                }
                notes.postValue(al)
            }
        return notes
    }
    fun addNote(note: String) {
        if (note.isNotEmpty()) {
            val n= hashMapOf(
                "note" to note
            )
            db.collection("notes").add(n)
            Toast.makeText(app, "note is added ", Toast.LENGTH_SHORT).show()
            getNote()
        }
        else
        {
            Toast.makeText(app, "please enter note", Toast.LENGTH_SHORT).show()
        }
    }

        fun updateNote(id:String,new:String) {
            db.collection("notes")
                .get()
                .addOnSuccessListener {
                        result->
                    for (note in result){
                        if (id == note.id){
                            db.collection("notes").document(id).update("note",new)
                            getNote()
                    }
                }
        }}

        fun delNote(id: String) {
            db.collection("notes")
                .get()
                .addOnSuccessListener {
                        result->
                    for (note in result){
                        if (note.id == id){
                            db.collection("notes").document(id).delete()
                            getNote()

                        }
                    }
                }
        }
    }