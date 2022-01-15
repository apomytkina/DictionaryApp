package com.example.dictionaryapp.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.dictionaryapp.model.Def

val differCallback = object : DiffUtil.ItemCallback<Def>() {
    override fun areItemsTheSame(oldItem: Def, newItem: Def): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: Def, newItem: Def): Boolean {
        return oldItem == newItem
    }
}