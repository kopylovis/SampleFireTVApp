package com.example.myapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.MenulayoutBinding
import com.example.myapplication.ui.viewholders.MenuItemViewHolder


class MenuItemsAdapter(private val items: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var clickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MenuItemViewHolder(
            MenulayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MenuItemViewHolder) {
            holder.clickListener = clickListener
            holder.bindView(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}