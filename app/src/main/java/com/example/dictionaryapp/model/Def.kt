package com.example.dictionaryapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "defs"
)
data class Def(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val pos: String,
    val text: String,
    val tr: List<Tr>,
    val ts: String
)