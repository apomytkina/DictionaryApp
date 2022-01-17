package com.example.dictionaryapp.adapters.searchAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.databinding.SearchWordCardBinding
import com.example.dictionaryapp.model.Def

class SearchDefViewHolder(
    private val binding: SearchWordCardBinding,
    addListener: SearchDefAdapter.OnItemClickListener,
    infoListener: SearchDefAdapter.OnItemClickListener
): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Def) {
        with (binding) {
            searchCardTv.text = "${data.text} - ${data.tr[0].text}"
        }
    }

    init {
        binding.addSearchCard.setOnClickListener {
            addListener.onItemClick(adapterPosition)
        }

        binding.infoSearchCard.setOnClickListener {
            infoListener.onItemClick(adapterPosition)
        }
    }
}