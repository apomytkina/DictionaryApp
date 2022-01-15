package com.example.dictionaryapp.db

import androidx.room.TypeConverter
import com.example.dictionaryapp.model.Def
import com.example.dictionaryapp.model.Ex
import com.example.dictionaryapp.model.Tr
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun ToTr(value: String): List<Tr> {
        val listType = object : TypeToken<List<Tr>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromTr(list: List<Tr>): String {
        return gson.toJson(list)
    }
}