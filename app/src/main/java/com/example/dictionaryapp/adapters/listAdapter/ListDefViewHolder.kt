package com.example.dictionaryapp.adapters.listAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.databinding.ListWordCardBinding
import com.example.dictionaryapp.model.Def

class ListDefViewHolder(
    private val binding: ListWordCardBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Def) {
        with (binding) {
            listCardTv.text = "${data.text} - ${data.tr[0].text}"
            addNotificationListCard.setOnClickListener{

            }
            deleteListCard.setOnClickListener {

            }
        }
    }
}