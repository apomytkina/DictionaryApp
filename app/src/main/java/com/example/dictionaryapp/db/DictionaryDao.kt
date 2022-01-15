package com.example.dictionaryapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dictionaryapp.model.Def

@Dao
interface DictionaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(def: Def): Long

    @Query("SELECT * FROM defs")
    fun getAllDefs(): LiveData<List<Def>>

    @Delete
    suspend fun deleteDef(def: Def)
}