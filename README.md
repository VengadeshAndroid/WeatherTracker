## Weather Tracker App
**Description**
Weather Tracker is an Android application that allows users to search for a city's weather and display current weather details.
The app uses WeatherAPI.com for fetching data and Jetpack Compose for a modern UI experience.

## Features

**Search and Save City**: Search for any city and save it for persistent weather tracking.
**Current Weather Details**: Display weather details for the selected city:
* Temperature
* Weather Condition (with icon)
* Humidity
* UV Index
* Feels-like Temperature
**Persistent Data Storage**: Saves the last selected city using SharedPreferences.
**Modern UI**: Built using Jetpack Compose with clean and responsive designs.

## Tech Stack
**Language**: Kotlin
**UI**: Jetpack Compose, Material3
**Architecture**: MVVM (Model-View-ViewModel)
**Dependency Injection**: Hilt
**Networking**: Retrofit with Moshi/Converter Gson
**Local Storage**: SharedPreferences
**Concurrency**: Coroutines
**Logging**: Timber
**Image Loading**: Coil (for weather icons)
**Navigation**: Jetpack Navigation Compose

## Project Setup

**Prerequisites**
Install Android Studio (Latest Version).
Set up a WeatherAPI.com account to get your API key.
**Steps**
1. Clone the repository:    
   - git clone https://github.com/VengadeshAndroid/WeatherTracker.git
   - cd WeatherTracker
2. Open the project in Android Studio.

3. Add your WeatherAPI key to the project:
   - Open MainActivity or the API service class.  
   - Replace the placeholder API_KEY with your actual API key.
4. Build and run the project:
   - Use the Run button in Android Studio or execute:
    ./gradlew assembleDebug

## Project Structure
app/
│-- src/
│   ├── main/
│   │   ├── java/com.nooro.weather/
│   │   │   ├── component/        # UI components
│   │   │   ├── model/            # Data models
│   │   │   ├── navigation/       # Navigation setup
│   │   │   ├── state/            # UI State management
│   │   │   ├── theme/            # Theme and styling
│   │   │   ├── util/             # Utility classes
│   │   │   ├── view/             # UI screens
│   │   │   ├── viewmodel/        # ViewModels for each screen
│   │   │   └── webservice/       # API service classes
│   │   │   └── MainActivity.kt   # App entry point
│   │   └── res/                  # Resources (layouts, images, strings)
│   
├── test/                         # Unit tests
└── androidTest/                  # Instrumentation tests

## Dependencies
Your project uses the following libraries:

* Jetpack Compose
* Retrofit for API calls
* Moshi/Gson for JSON parsing
* Hilt for dependency injection
* Coil for image loading
* Timber for logging
* JUnit and Espresso for testing.

## Key Highlights
**Clean Architecture**: Separation of concerns with MVVM.
**Modern UI**: Jetpack Compose for reactive, declarative UI.
**Error Handling**: Handles invalid cities, network issues gracefully.
