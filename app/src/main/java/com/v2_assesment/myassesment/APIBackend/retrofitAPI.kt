package com.v2_assesment.myassesment.APIBackend

import retrofit2.http.GET


interface QuestionsAPI {
    @GET("b/687374506063391d31aca23a")
    suspend fun questionsAPI(): ResponseData

}
