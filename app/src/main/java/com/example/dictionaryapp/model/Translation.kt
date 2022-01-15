package com.example.dictionaryapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(
//    tableName = "translations"
//)
data class Translation(
//    @PrimaryKey(autoGenerate = true)
//    var id: Int? = null,
    val def: List<Def>,
    val head: Head
)