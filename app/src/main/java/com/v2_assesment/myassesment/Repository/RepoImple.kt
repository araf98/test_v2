package com.v2_assesment.myassesment.Repository


import com.v2_assesment.myassesment.APIBackend.QuestionAPIClient
import com.v2_assesment.myassesment.APIBackend.QuestionsAPI
import com.v2_assesment.myassesment.APIBackend.Record
import com.v2_assesment.myassesment.APIBackend.ResponseData
import com.v2_assesment.myassesment.Room.OptionsEntity
import com.v2_assesment.myassesment.Room.RecordDao
import com.v2_assesment.myassesment.Room.RecordEntity
import com.v2_assesment.myassesment.Room.UserAnswer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class RepoImple @Inject constructor(
    private val dao: RecordDao,
    private val questionsAPI: QuestionsAPI

) : Repository {

    override suspend fun loadQuestions(): Flow<List<RecordEntity>> = flow{
       emit(dao.getAllRecords())
    }

    override suspend fun loadOptions(): Flow<List<OptionsEntity>> = flow {
        emit(dao.getAllOptionsByRecordId())
    }

        override suspend fun fetchDataFromAPI(): ResponseData {
            return questionsAPI.questionsAPI() // Use the injected API service
        }

    override suspend fun insertIntoOptions(options:  List<OptionsEntity>) {
        dao.insertOptions(options)
    }

    override suspend fun insetIntoRecord(records: List<RecordEntity>) {
        dao.insertRecords(records)
    }
    override suspend fun insertUserAnswer(userAnswers: List<UserAnswer>)  {
        dao.insertUserAnswer(userAnswers)
    }


    override suspend fun getSavedAnswers(): List<UserAnswer> {
        return dao.getAllSavedAnswers()
    }

    override suspend fun saveFullSubmission(records: List<Record>, answers: List<Pair<Int, String>>) {
        records.forEach { record ->
            dao.insertRecordEntity(
                RecordEntity(
                    id = record.id.toString(),
                    skip = record.skip.id,
                    type = record.type,
                    question = record.question.slug,
                    validations = record.validations?.regex ?: "",
                    referTo = record.referTo?.id ?: ""
                )
            )

            record.options?.forEach { option ->
                dao.insertOptionEntity(
                    OptionsEntity(
                        recordId = record.id.toString(),
                        referTo = option.referTo?.id ?: "",
                        value = option.value ?: ""
                    )
                )
            }
        }

        val answerString = answers.joinToString("\n") { "QID ${it.first}: ${it.second}" }
        dao.insertUserAnswer(listOf(UserAnswer(answer = answerString)))
    }



}