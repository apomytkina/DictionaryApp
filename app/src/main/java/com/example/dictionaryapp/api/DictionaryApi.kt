package com.example.dictionaryapp.api

import com.example.dictionaryapp.model.Translation
import com.example.dictionaryapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApi {
    @GET("lookup")
    suspend fun getTranslation(
        @Query("key")
        key: String = API_KEY,
        @Query("lang")
        lang: String = "en-ru",
        @Query("text")
        text: String = "time"
    ) : Response<Translation>
}