package com.v2_assesment.myassesment.APIBackend

data class ResponseData(
    val metadata: Metadata,
    val record: List<Record>
)

data class Metadata(
    val createdAt: String,
    val id: String,
    val name: String,
    val `private`: Boolean
)

data class Record(
    val id: Int,
    val skip: Skip,
    val type: String,
    val question: Question,
    val options: List<Option>?,
    val validations: Validations ? = null,
    val referTo: ReferToX
)

data class Option(
    val referTo: ReferToX?= null,
    val value: String?
)

data class Question(
    val slug: String
)

data class ReferToX(
    val id: String?
)

data class Skip(
    val id: String
)

data class Validations(
    val regex: String?
)