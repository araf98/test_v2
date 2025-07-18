package com.v2_assesment.myassesment.Navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.v2_assesment.myassesment.APIBackend.Question
import com.v2_assesment.myassesment.APIBackend.Record
import com.v2_assesment.myassesment.Screens.QuestionCard
import com.v2_assesment.myassesment.Screens.SavedScreen
import com.v2_assesment.myassesment.ViewModel.QuestionsViewModel

@Composable
fun QuestionAppNavigation() {
    val viewModel: QuestionsViewModel = hiltViewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "QuestionCard") {
        composable("QuestionCard") {
            val records: List<Record>? = viewModel.responseData.value?.record
            val allQuestions:List<Question>? = records?.map { it.question }
            val currentRecord : Record? = records?.find { it.id == viewModel.currentQuestionId?.value }
            val currentQuestion: Question? = currentRecord?.question

            if (records != null) {
                if (currentRecord != null) {
                    QuestionCard(
                        viewModel = viewModel,
                        currentRecord = currentRecord,
                        recordList = records,
                        navController = navController,
                        onSubmit = {
                            navController.navigate("SavedScreens")
                        }
                    )
                }
            }

        }

        composable("SavedScreens") {
            SavedScreen(navController = navController, viewModel = viewModel)
        }
    }
}
