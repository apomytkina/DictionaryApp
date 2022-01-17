package com.example.dictionaryapp.adapters.listAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.databinding.ListWordCardBinding
import com.example.dictionaryapp.model.Def

class ListDefViewHolder(
    private val binding: ListWordCardBinding,
    listener: ListDefAdapter.OnItemClickListener
): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Def) {
        with (binding) {
            listCardTv.text = "${data.text} - ${data.tr[0].text}"
        }
    }

    init {
        binding.addNotificationListCard.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }

        binding.deleteListCard.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
    }
}