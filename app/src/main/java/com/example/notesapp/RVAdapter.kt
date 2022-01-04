package com.example.notesapp

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.RowBinding


class RVAdapter (private var list: List<Data>): RecyclerView.Adapter<RVAdapter.Holder>() {

    class Holder( val Binding: RowBinding): RecyclerView.ViewHolder(Binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(RowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list[position]
        Log.d("nnnnnn","$data")
        holder.Binding.apply {
            tvTitle.text = data.title
            tvNote.text = data.note

            holder.itemView.setOnClickListener{
                val data = Data( list[position].id, list[position].title, list[position].note)
                val intent = Intent(holder.itemView.context, ShowNotes::class.java)
                intent.putExtra("displayData",data)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = list.size
}