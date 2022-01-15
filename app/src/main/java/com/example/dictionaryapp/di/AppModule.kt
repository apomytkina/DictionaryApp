package com.example.dictionaryapp.di

import android.content.Context
import androidx.room.Room
import com.example.dictionaryapp.api.DictionaryApi
import com.example.dictionaryapp.db.DictionaryDatabase
import com.example.dictionaryapp.util.Constants
import com.example.dictionaryapp.util.Constants.Companion.DICTIONARY_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDictionaryDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        DictionaryDatabase::class.java,
        DICTIONARY_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDictionaryDao(db: DictionaryDatabase) = db.getDictionaryDao()

    @Singleton
    @Provides
    fun provideRetrofitInstance(): DictionaryApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
}