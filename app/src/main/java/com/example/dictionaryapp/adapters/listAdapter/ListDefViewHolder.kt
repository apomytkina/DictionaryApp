package com.example.dictionaryapp.adapters.listAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.databinding.ListWordCardBinding
import com.example.dictionaryapp.model.Def

class ListDefViewHolder(
    private val binding: ListWordCardBinding,
    infoListener: ListDefAdapter.OnItemClickListener,
    deleteListener: ListDefAdapter.OnItemClickListener
    //addNotificationListener: ListDefAdapter.OnItemClickListener
): RecyclerView.ViewHolder(binding.root) {
    private lateinit var def: Def

    fun bind(data: Def) {
        with (binding) {
            listCardTv.text = "${data.text} - ${data.tr[0].text}"
            def = data
        }
    }

    init {
        binding.infoListCard.setOnClickListener {
            infoListener.onItemClick(def)
        }

        binding.deleteListCard.setOnClickListener {
            deleteListener.onItemClick(def)
        }

//        binding.addNotificationListCard.setOnClickListener {
//            addNotificationListener.onItemClick(def)
//        }
    }
}