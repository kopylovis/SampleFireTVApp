package com.example.myapplication.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CardlayoutBinding
import com.example.myapplication.databinding.MenulayoutBinding

class MenuItemViewHolder(private val binding: MenulayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var clickListener: ((String) -> Unit)? = null

    fun bindView(text: String) {
        binding.apply {
            textView.text = text
            menuconstraintLayout.setOnClickListener {
                clickListener?.invoke(text)
            }
        }
    }

}