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

    @Query("SELECT COUNT(*) FROM defs")
    fun getTranslationsSize(): LiveData<Int>

    @Delete
    suspend fun deleteTranslation(def: Def)
}