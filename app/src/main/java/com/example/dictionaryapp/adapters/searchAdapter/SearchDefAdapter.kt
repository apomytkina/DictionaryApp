package com.example.dictionaryapp.adapters.searchAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.dictionaryapp.adapters.differCallback
import com.example.dictionaryapp.databinding.SearchWordCardBinding
import com.example.dictionaryapp.model.Def

class SearchDefAdapter(private var listener: OnItemClickListener):
    ListAdapter<Def, SearchDefViewHolder>(differCallback) {
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchDefViewHolder {
        return SearchDefViewHolder(
            SearchWordCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: SearchDefViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}