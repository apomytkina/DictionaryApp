package com.example.dictionaryapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.model.Def
import com.example.dictionaryapp.model.Translation
import com.example.dictionaryapp.repository.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val repository: DictionaryRepository
): ViewModel() {
    private val _def = MutableLiveData<Def>()
    val def: LiveData<Def> = _def

    fun getTranslation() = viewModelScope.launch {
        repository.getTranslation().let { response ->
            val responseTranslation = response.body()
            if (response.isSuccessful && responseTranslation != null)
                _def.postValue(responseTranslation.def[0])
            else
                Log.d("tag", "getTranslation Error: ${response.code()}")
        }
    }

    fun saveWord(translation: Translation) = viewModelScope.launch {
        repository.upsert(translation.def[0])
    }

    fun getAllWords() = repository.getAllTranslations()

    fun deleteWord(translation: Translation) = viewModelScope.launch {
        repository.deleteTranslation(translation.def[0])
    }

    init {
        getTranslation()
    }
}