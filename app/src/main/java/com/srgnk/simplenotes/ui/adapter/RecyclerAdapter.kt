package com.srgnk.simplenotes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.srgnk.simplenotes.databinding.NoteBinding
import com.srgnk.simplenotes.mvp.model.Note
import com.srgnk.simplenotes.utils.getFormattedDate
import java.util.*

class RecyclerAdapter(
    private var values: MutableList<Note>,
    private val itemClickListener: RecyclerViewClickListener
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    interface RecyclerViewClickListener {
        fun recyclerViewClickListener(noteId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = NoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = values[position].title
        holder.content.text = values[position].content
        holder.date.text = getFormattedDate(Date(values[position].date))
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(binding: NoteBinding, private val listener: RecyclerViewClickListener) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        var title = binding.noteTitle
        var content = binding.noteContent
        var date = binding.noteDate

        init {
            binding.note.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            listener.recyclerViewClickListener(values[adapterPosition].id)
        }
    }
}