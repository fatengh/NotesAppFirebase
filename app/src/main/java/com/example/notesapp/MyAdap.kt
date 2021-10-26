package com.example.notesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class MyAdap ( private val activity: MainActivity, private var notes: ArrayList<Note>): RecyclerView.Adapter<MyAdap.ItemViewHolder>() {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
      val note = notes[position]

        holder.itemView.apply {
            tv.text = note.note
            imEd.setOnClickListener {
                activity.raiseDialog(note.id)
            }
            imDel.setOnClickListener {
               activity.deleteNote(note.id)
            }
        }
    }

    override fun getItemCount() = notes.size


}