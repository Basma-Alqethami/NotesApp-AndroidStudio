package com.example.notesapp

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row.view.*

class RVAdapter (private var list: ArrayList<Data>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = list[position]

        holder.itemView.apply {
            tvTitle.text = data.title
            tvNote.text = data.note

            holder.itemView.setOnClickListener{
                val data = Data( list[position].id.toInt(), list[position].title, list[position].note)
                val intent = Intent(holder.itemView.context, ShowNotes::class.java)
                intent.putExtra("displayData",data)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = list.size
}