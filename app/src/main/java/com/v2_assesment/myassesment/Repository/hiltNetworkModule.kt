package com.v2_assesment.myassesment.Repository

import com.v2_assesment.myassesment.APIBackend.QuestionsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
    return Retrofit.Builder()
        .baseUrl("https://api.jsonbin.io/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }

    @Singleton
    @Provides
    fun providesQuestionAPI(retrofit: Retrofit): QuestionsAPI{
        return retrofit.create(QuestionsAPI::class.java)
    }

}