package com.v2_assesment.myassesment.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecordDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecords(records: List<RecordEntity>)

    @Query("select * from testTable order by  id ")
    suspend fun getAllRecords():  List<RecordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOptions(option: List<OptionsEntity>)

    @Query("select * from optionsTable order by recordId")
    suspend fun getAllOptionsByRecordId() : List<OptionsEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAnswer(answer: List<UserAnswer>)
    @Query("select * from UserAnswer")
    suspend fun getAllSavedAnswers(): List<UserAnswer>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecordEntity(record: RecordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOptionEntity(option: OptionsEntity)

}