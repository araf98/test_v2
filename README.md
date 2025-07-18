# 🧠 MyAssessment App

An Android application built with **Jetpack Compose**, featuring a dynamic question-answer flow, local persistence using **Room**, and screen-to-screen navigation using **Jetpack Navigation**.

---

## 🚀 Features

- ✅ Dynamic question rendering based on JSON logic (`referTo`, `skip`)
- 📥 Saves user answers in a local Room database
- 🔄 "Skip" logic and dynamic navigation based on question metadata
- 🧾 View all saved answers in a scrollable list
- 🏠 Home navigation & activity refresh

---

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Toolkit**: Jetpack Compose
- **Architecture**: MVVM
- **Dependency Injection**: Hilt(App modules, Network Modules)
- **Database**: Room
- **Navigation**: Jetpack Navigation
- **Other**: State Management, MutableStateList, LaunchedEffect, Scaffold, LazyColumn

---

## 🧩 Modules

### 1. `QuestionCard` Screen
Displays a single question with dynamic input UI based on the type (e.g., dropdown, checkbox, text), handles:
- Skipping questions via metadata
- Moving to the next question via `referTo`
- Saving answers to the Room database

### 2. `SavedScreen`
- Fetches and displays all previously submitted answers
- Provides buttons to:
  - Refresh the screen
  - Navigate back to the main questionnaire

---

## 📦 Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/araf98/mytest_v2.git
