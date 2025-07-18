package com.v2_assesment.myassesment.Screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.v2_assesment.myassesment.Room.UserAnswer
import com.v2_assesment.myassesment.ViewModel.QuestionsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SavedScreen(navController: NavController, viewModel: QuestionsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val answers = remember { mutableStateListOf<UserAnswer>() }

    LaunchedEffect(Unit) {
        val saved = viewModel.getSavedAnswers()
        answers.clear()
        answers.addAll(saved)
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(answers) { answer ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Submitted Answers:")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = answer.answer)
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = { (context as? Activity)?.recreate() },
                            ) {
                                Text("Refresh Activity")
                            }
                            Button(onClick = { navController.navigate("QuestionCard") }) {
                                Text("Home Screen")
                            }
                        }
                    }
                }
            }
        }
    }
}
