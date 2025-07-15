package com.example.myapplication

/**
 * A sealed hierarchy describing the state of the poker session management.
 */
sealed interface UiState {

    /**
     * Empty state when the screen is first shown
     */
    object Initial : UiState

    /**
     * Still loading
     */
    object Loading : UiState

    /**
     * Success state
     */
    object Success : UiState

    /**
     * There was an error
     */
    data class Error(val errorMessage: String) : UiState
}

/**
 * States for the different screens in the poker app
 */
enum class ScreenState {
    Players,
    Boxes,
    FinalCount,
    Summary,
    History,
    SessionDetails
}