package com.v2_assesment.myassesment.Screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import com.v2_assesment.myassesment.ViewModel.QuestionsViewModel

import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.v2_assesment.myassesment.APIBackend.Option

import com.v2_assesment.myassesment.APIBackend.Record
import com.v2_assesment.myassesment.APIBackend.ReferToX

@Composable
fun QuestionCard(
    currentRecord: Record,
    recordList: List<Record>,
    viewModel: QuestionsViewModel,
    onSubmit: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current

    var input by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val checkedItems = remember { mutableStateListOf<String>() }
    var captured by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val path = viewModel.saveBitmapToInternalStorage(context, bitmap)
            viewModel.saveAnswer(currentRecord.id, path)
            val nextId = currentRecord.referTo.id?.toIntOrNull()
            nextId?.let { viewModel.setCurrentQuestionId(it) }
            captured = true
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Q${currentRecord.id}: ${currentRecord.question.slug}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        when (currentRecord.type.lowercase()) {
            "textinput", "numberinput" -> {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    label = { Text("Answer") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = if (currentRecord.type.lowercase() == "numberinput")
                        KeyboardOptions(keyboardType = KeyboardType.Number)
                    else KeyboardOptions.Default
                )
            }

            "dropdown" -> {
                val options = currentRecord.options ?: emptyList()

                Box {
                    OutlinedTextField(
                        value = selectedOption ?: "",
                        onValueChange = { },
                        label = { Text("Choose one") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true },
                        readOnly = true
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option.value ?: "") },
                                onClick = {
                                    selectedOption = option.value
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            "checkbox", "multiplechoice", "multiplchoice" -> {
                val options = currentRecord.options ?: emptyList()
                LazyColumn {
                    items(options.size) { index ->
                        val option = options[index]
                        val optionValue = option.value ?: ""
                        val isChecked = checkedItems.contains(optionValue)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = {
                                    if (isChecked) checkedItems.remove(optionValue)
                                    else checkedItems.add(optionValue)
                                }
                            )
                            Text(optionValue)
                        }
                    }
                }
            }

            "camera" -> {
                Button(onClick = { launcher.launch() }) {
                    Text("Capture Photo")
                }
                if (captured) {
                    Text("Photo saved successfully", color = Color.Green)
                }
            }

            else -> {
                Text("Unsupported question type")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Show skip button only if skip.id != "-1"
        if (!currentRecord.skip?.id.isNullOrBlank() && currentRecord.skip?.id != "-1") {
            Button(
                onClick = {
                    val skipId = currentRecord.skip?.id?.toIntOrNull()
                    skipId?.let { viewModel.setCurrentQuestionId(it) }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Skip")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        val isSubmit = currentRecord.referTo?.id == "submit"

        Button(
            onClick = {
                val answer = when (currentRecord.type.lowercase()) {
                    "checkbox", "multiplechoice", "multiplchoice" -> checkedItems.joinToString()
                    "dropdown" -> selectedOption ?: ""
                    "camera" -> ""
                    else -> input
                }

                // Save answer unless it's a camera input (already saved post-capture)
                if (currentRecord.type.lowercase() != "camera") {
                    viewModel.saveAnswer(currentRecord.id, answer)
                }

                if (isSubmit) {
                    viewModel.submitAnswers()
                    onSubmit()
                    return@Button
                }

                // Determine nextId from skip, referTo, or option-level referTo
                val nextId: Int? = when {
                    !currentRecord.skip?.id.isNullOrBlank() && currentRecord.skip?.id != "-1" ->
                        currentRecord.skip?.id?.toIntOrNull()

                    !currentRecord.referTo?.id.isNullOrBlank() && currentRecord.referTo?.id != "submit" ->
                        currentRecord.referTo?.id?.toIntOrNull()

                    currentRecord.options?.any { it.referTo?.id != null } == true -> {
                        currentRecord.options
                            ?.firstOrNull { it.referTo?.id != null }
                            ?.referTo?.id
                            ?.toIntOrNull()
                    }

                    else -> null
                }

                nextId?.let { id ->
                    recordList.find { it.id == id }?.let {
                        viewModel.setCurrentQuestionId(it.id)
                    }
                }

                // Reset input states
                input = ""
                selectedOption = null
                checkedItems.clear()
                captured = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isSubmit) "Submit" else "Next")
        }
    }
}