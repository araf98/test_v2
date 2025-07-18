package com.v2_assesment.myassesment.Repository

import com.v2_assesment.myassesment.APIBackend.Record
import com.v2_assesment.myassesment.APIBackend.ResponseData
import com.v2_assesment.myassesment.Room.OptionsEntity
import com.v2_assesment.myassesment.Room.RecordEntity
import com.v2_assesment.myassesment.Room.UserAnswer
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun loadQuestions(): Flow<List<RecordEntity>>

    suspend fun loadOptions(): Flow<List<OptionsEntity>>

    suspend fun insetIntoRecord(records: List<RecordEntity>)
    suspend fun insertIntoOptions(options :  List<OptionsEntity>)

    suspend fun fetchDataFromAPI(): ResponseData
    suspend fun insertUserAnswer(answerString:List<UserAnswer>)
    suspend fun getSavedAnswers(): List<UserAnswer>
    suspend fun saveFullSubmission(records: List<Record>, answers: List<Pair<Int, String>>)

}