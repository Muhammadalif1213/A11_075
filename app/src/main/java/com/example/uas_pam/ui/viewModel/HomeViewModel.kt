package com.example.uas_pam.ui.viewModel

import androidx.lifecycle.ViewModel

class HomeViewModel(
): ViewModel(){
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)