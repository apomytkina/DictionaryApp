package com.example.dictionaryapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionaryapp.model.Def

@Database(
    entities = [Def::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun getDictionaryDao(): DictionaryDao
}