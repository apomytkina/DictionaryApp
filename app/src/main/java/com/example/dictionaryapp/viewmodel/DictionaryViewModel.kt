package com.example.dictionaryapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.model.Def
import com.example.dictionaryapp.repository.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val repository: DictionaryRepository
): ViewModel() {
    private val _def = MutableLiveData<List<Def>>()
    val def: LiveData<List<Def>> = _def

    fun getTranslation(text: String) = viewModelScope.launch {
        repository.getTranslation(text).let { response ->
            val responseTranslation = response.body()
            if (response.isSuccessful && responseTranslation != null)
                _def.postValue(responseTranslation.def)
            else
                Log.d("tag", "getTranslation Error: ${response.code()}")
        }
    }

    fun saveWord(def: Def) = viewModelScope.launch {
        repository.upsert(def)
    }

    fun getAllWords() = repository.getAllTranslations()

    fun getRandomTranslations() = repository.getRandomTranslations()

    fun deleteWord(def: Def) = viewModelScope.launch {
        repository.deleteTranslation(def)
    }

    fun getSize() = repository.getTranslationsSize()
}