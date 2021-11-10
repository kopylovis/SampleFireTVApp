package com.example.myapplication.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CardlayoutBinding

class RowViewHolder(private val binding: CardlayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var clickListener: ((String, Int) -> Unit)? = null

    fun bindView(text: String) {
        binding.apply {
            textView.text = text
            cardconstraintLayout.setOnClickListener {
                clickListener?.invoke(text, adapterPosition)
            }
        }
    }

}