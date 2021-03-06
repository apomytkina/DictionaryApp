package com.example.dictionaryapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dictionaryapp.model.Def
import com.example.dictionaryapp.model.Translation

@Dao
interface DictionaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(def: Def): Long

    @Query("SELECT * FROM defs")
    fun getAllTranslations(): LiveData<List<Def>>

    @Query("SELECT * FROM defs ORDER BY RANDOM() LIMIT 5")
    fun getRandomTranslations(): LiveData<List<Def>>

    @Query("SELECT COUNT(*) FROM defs")
    fun getTranslationsSize(): LiveData<Int>

    @Query("SELECT * FROM defs ORDER BY RANDOM() LIMIT 1")
    fun getRandomWordForNotification() : LiveData<Def>

    @Delete
    suspend fun deleteTranslation(def: Def)
}