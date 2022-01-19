package com.example.dictionaryapp.repository

import com.example.dictionaryapp.api.DictionaryApi
import com.example.dictionaryapp.db.DictionaryDao
import com.example.dictionaryapp.model.Def
import com.example.dictionaryapp.model.Translation
import javax.inject.Inject

class DictionaryRepository @Inject constructor(
    private val dictionaryDao: DictionaryDao,
    private val dictionaryApi: DictionaryApi
) {
    suspend fun getTranslation(text: String) = dictionaryApi.getTranslation(text=text)

    suspend fun upsert(def: Def) = dictionaryDao.upsert(def)

    fun getAllTranslations() = dictionaryDao.getAllTranslations()

    fun getTranslationsSize() = dictionaryDao.getTranslationsSize()

    suspend fun deleteTranslation(def: Def) = dictionaryDao.deleteTranslation(def)
}