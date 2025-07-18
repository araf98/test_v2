package com.v2_assesment.myassesment.APIBackend

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//not used
object QuestionAPIClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.jsonbin.io/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api : QuestionsAPI = retrofit.create(QuestionsAPI::class.java)
}