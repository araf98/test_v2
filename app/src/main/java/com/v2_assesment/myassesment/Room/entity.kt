package com.v2_assesment.myassesment.Room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "testTable")
data class RecordEntity(
    @PrimaryKey val id: String,
    val skip: String,
    val type: String,
    val question: String,
    val validations: String,
    val referTo: String

)

@Entity(tableName = "optionsTable")
data class OptionsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val recordId : String,
    val referTo: String,
    val value: String

)
@Entity(tableName = "userAnswer")
data class UserAnswer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val answer: String
)