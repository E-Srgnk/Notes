package com.srgnk.simplenotes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.srgnk.simplenotes.R
import com.srgnk.simplenotes.mvp.model.Note
import com.srgnk.simplenotes.mvp.utils.getFormattedDate
import kotlinx.android.synthetic.main.note.view.*
import java.util.*

class RecyclerAdapter(
    private var values: MutableList<Note>,
    private val itemClickListener: RecyclerViewClickListener
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    interface RecyclerViewClickListener {
        fun recyclerViewClickListener(noteId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.note, parent, false)
        return ViewHolder(itemView, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = values[position].title
        holder.content.text = values[position].content
        holder.date.text = getFormattedDate(Date(values[position].date))
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View, private val listener: RecyclerViewClickListener) :
        RecyclerView.ViewHolder(view.rootView),
        View.OnClickListener {

        var title: TextView = view.note_title
        var content: TextView = view.note_content
        var date: TextView = view.note_date

        init {
            view.note.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            listener.recyclerViewClickListener(values[adapterPosition].id)
        }
    }
}