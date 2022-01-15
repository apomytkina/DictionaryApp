package com.example.dictionaryapp.adapters.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.dictionaryapp.adapters.differCallback
import com.example.dictionaryapp.databinding.ListWordCardBinding
import com.example.dictionaryapp.model.Def

class ListDefAdapter : androidx.recyclerview.widget.ListAdapter<Def, ListDefViewHolder>(differCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDefViewHolder {
        return ListDefViewHolder(
            ListWordCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListDefViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}