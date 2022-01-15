package com.example.dictionaryapp.repository

import com.example.dictionaryapp.api.DictionaryApi
import com.example.dictionaryapp.db.DictionaryDao
import com.example.dictionaryapp.model.Def
import javax.inject.Inject

class DictionaryRepository @Inject constructor(
    private val dictionaryDao: DictionaryDao,
    private val dictionaryApi: DictionaryApi
) {
    suspend fun getTranslation() = dictionaryApi.getTranslation()

    suspend fun upsert(def: Def) = dictionaryDao.upsert(def)

    fun getAllDefs() = dictionaryDao.getAllDefs()

    suspend fun deleteDef(def: Def) = dictionaryDao.deleteDef(def)
}