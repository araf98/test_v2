package com.v2_assesment.myassesment.ViewModel

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v2_assesment.myassesment.APIBackend.ResponseData
import com.v2_assesment.myassesment.Repository.Repository
import com.v2_assesment.myassesment.Room.UserAnswer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    // Holds the fetched question data response
    private val _responseData = mutableStateOf<ResponseData?>(null)
    val responseData: State<ResponseData?> = _responseData

    // Used for scrolling state in LazyColumn (if needed)
    val listState = LazyListState()

    // Current active question ID
    private val _currentQuestionId = mutableStateOf<Int?>(null)
    val currentQuestionId: State<Int?> = _currentQuestionId

    // Stores answers as pairs of (questionId, answer)
    private val _answers = mutableStateListOf<Pair<Int, String>>()
    val answers: List<Pair<Int, String>> = _answers

    // Fetch questions from API through repository
    fun fetchQuestions() {
        viewModelScope.launch {
            try {
                val results = repository.fetchDataFromAPI()
                _responseData.value = results
                // Set first question as current
                _currentQuestionId.value = results.record.firstOrNull()?.id
            } catch (e: Exception) {
                Log.e("API", "Error: ${e.message}")
            }
        }
    }

    // Update current question ID (navigate to question)
    fun setCurrentQuestionId(id: Int?) {
        _currentQuestionId.value = id
    }

    // Save or update answer for a question
    fun saveAnswer(questionId: Int, answer: String) {
        _answers.removeAll { it.first == questionId }
        _answers.add(questionId to answer)
    }

    // Submit all answers, save in DB via repository, reset state
    fun submitAnswers() {
        viewModelScope.launch {
            responseData.value?.let { response ->
                repository.saveFullSubmission(response.record, answers)
                _answers.clear()
                _currentQuestionId.value = response.record.firstOrNull()?.id
            }
        }
    }

    // Retrieve saved answers from database
    suspend fun getSavedAnswers(): List<UserAnswer> {
        return repository.getSavedAnswers()
    }

    // Save captured bitmap to internal storage and return the saved file path
    fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap): String {
        val filename = "photo_${System.currentTimeMillis()}.png"
        val file = File(context.filesDir, filename)
        FileOutputStream(file).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
        return file.absolutePath
    }
}
