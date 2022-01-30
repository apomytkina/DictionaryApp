package com.example.dictionaryapp.adapters.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.dictionaryapp.adapters.differCallback
import com.example.dictionaryapp.databinding.ListWordCardBinding
import com.example.dictionaryapp.model.Def

class ListDefAdapter(
    private var infoListener: OnItemClickListener,
    private var deleteListener: OnItemClickListener,
    //private var addNotificationListener: OnItemClickListener
):
    ListAdapter<Def, ListDefViewHolder>(differCallback) {
    interface OnItemClickListener {
        fun onItemClick(def: Def)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDefViewHolder {
        return ListDefViewHolder(
            ListWordCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            infoListener,
            deleteListener
            //addNotificationListener
        )
    }

    override fun onBindViewHolder(holder: ListDefViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}