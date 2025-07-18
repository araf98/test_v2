# V2 Assessment App

An Android app built with Jetpack Compose and MVVM architecture to deliver dynamic question flows with multiple input types, camera integration, and offline data persistence using Room database.

---

## Features

- **Dynamic Question Flow**: Loads questions from a network API and displays them one by one based on the question type.
- **Multiple Input Types**: Supports text input, number input, multiple choice, checkbox, dropdown, and camera photo capture.
- **Camera Integration**: Capture photos as answers and save them locally.
- **Offline Persistence**: Saves questions, options, and user answers in Room database for offline access.
- **Submit & Review**: Submit answers and view saved responses in a separate screen.
- **Navigation**: Easy navigation between question screen and saved answers screen using Jetpack Navigation Compose.
- **Modern Architecture**: Uses MVVM with Hilt for dependency injection and Kotlin Coroutines for async operations.

---

## Project Pipeline

1. **App Launch & Initialization**
    - Hilt initializes dependency injection modules.
    - ViewModel is injected and ready.

2. **Fetching Data**
    - ViewModel calls Repository to fetch question data from API.
    - Repository uses Retrofit to get the question JSON.
    - Data parsed into data classes (`Record`, `Option` etc.).

3. **Displaying Questions**
    - ViewModel exposes current question ID and question list as state.
    - Compose UI observes state and displays question card accordingly.
    - Question type decides the input UI (text, multiple choice, camera, etc.).

4. **Answer Collection & Navigation**
    - User inputs answer, saved in ViewModel state.
    - Next button or option selection updates current question ID.
    - Loop continues until all questions answered.

5. **Submission**
    - On Submit, ViewModel sends answers and questions to Repository.
    - Repository saves questions, options, and answers into Room database.

6. **Review Saved Answers**
    - User navigates to saved answers screen.
    - ViewModel fetches saved answers from Room.
    - UI displays stored answers with refresh capability.

---

## Dependency Injection (DI) with Hilt

- **AppModule.kt**
    - Provides Retrofit API client instance.
    - Provides Room database and DAO instances.
    - Provides Repository implementation.

- **ViewModel Injection**
    - `QuestionsViewModel` is annotated with `@HiltViewModel` and receives `Repository` via constructor injection.

- **Benefits**
    - Decouples dependencies.
    - Improves testability.
    - Simplifies lifecycle management and scope of components.

---

## Setup Instructions

### Prerequisites

- Android Studio Bumblebee or later
- Android device or emulator with Camera support
- Minimum SDK: 21
- Kotlin 1.7+
- Hilt, Jetpack Compose libraries included in `build.gradle`

### Getting Started

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/v2-assessment-app.git
   cd v2-assessment-app
