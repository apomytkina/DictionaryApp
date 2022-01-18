package com.example.dictionaryapp.adapters.searchAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.dictionaryapp.adapters.differCallback
import com.example.dictionaryapp.databinding.SearchWordCardBinding
import com.example.dictionaryapp.model.Def

class SearchDefAdapter(
    private var infoListener: OnItemClickListener,
    private var addListener: OnItemClickListener
):
    ListAdapter<Def, SearchDefViewHolder>(differCallback) {
    interface OnItemClickListener {
        fun onItemClick(def: Def)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchDefViewHolder {
        return SearchDefViewHolder(
            SearchWordCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            addListener,
            infoListener
        )
    }

    override fun onBindViewHolder(holder: SearchDefViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}