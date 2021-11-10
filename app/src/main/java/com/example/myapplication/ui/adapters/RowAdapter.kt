package com.example.myapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CardlayoutBinding
import com.example.myapplication.ui.viewholders.RowViewHolder


class RowAdapter(private val items: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var clickListener: ((String, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RowViewHolder(
            CardlayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RowViewHolder) {
            holder.clickListener = clickListener
            holder.bindView(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}