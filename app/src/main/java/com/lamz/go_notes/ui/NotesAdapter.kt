package com.lamz.go_notes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lamz.go_notes.domain.Notes
import com.lamz.gonotes.databinding.ItemNotesBinding

class NotesAdapter(private val noteList: List<Notes>) : RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {
    interface OnItemClick{
        fun onItemClicked(notes: Notes)
    }

    private lateinit var onItemClick : OnItemClick
    fun setOnItemClick(onItemClick : OnItemClick){
        this.onItemClick = onItemClick
    }

    class MyViewHolder(private val binding : ItemNotesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notes: Notes) {
            with(binding){
                noteTitle.text = notes.title
                noteDesc.text = notes.desc
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = noteList[position]
        holder.bind(note)
        holder.itemView.setOnClickListener {
            onItemClick.onItemClicked(noteList[holder.adapterPosition])
        }
    }
}