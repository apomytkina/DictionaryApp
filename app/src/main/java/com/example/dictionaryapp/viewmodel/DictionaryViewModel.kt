package com.example.dictionaryapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.repository.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val repository: DictionaryRepository
): ViewModel() {

}