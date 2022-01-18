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

//    @Query("SELECT COUNT(*) FROM defs")
//    fun getTranslationsSize(): LiveData<List<Def>>

//    @Query("SELECT * FROM defs WHERE text=:word")
//    fun findTranslation(word: String): LiveData<List<Def>>

    @Delete
    suspend fun deleteTranslation(def: Def)
}