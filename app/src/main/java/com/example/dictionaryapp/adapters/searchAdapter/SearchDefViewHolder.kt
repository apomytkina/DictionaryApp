package com.example.dictionaryapp.adapters.searchAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.databinding.SearchWordCardBinding
import com.example.dictionaryapp.model.Def

class SearchDefViewHolder(
    private val binding: SearchWordCardBinding,
    listener: SearchDefAdapter.OnItemClickListener
): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Def) {
        with (binding) {
            searchCardTv.text = "${data.text} - ${data.tr[0].text}"
        }
    }

    init {
        binding.addSearchCard.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }

        binding.searchCardTv.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }

        binding.infoSearchCard.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
    }
}