package com.v2_assesment.myassesment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.v2_assesment.myassesment.Navigation.QuestionAppNavigation

import com.v2_assesment.myassesment.ViewModel.QuestionsViewModel
import com.v2_assesment.myassesment.ui.theme.Test_V2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            Test_V2Theme {
                val viewModel : QuestionsViewModel = hiltViewModel()
                LaunchedEffect(Unit) {
                    viewModel.fetchQuestions()

                }

                QuestionAppNavigation()

            }
            //  // Your Compose content
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Test_V2Theme {

    }
}