package com.example.uas_pam.ui.viewModel.Buku

import com.example.uas_pam.model.Buku

sealed class ListBukuUiState{
    object Loading: ListBukuUiState()
    object Error: ListBukuUiState()
    data class Success(val listBuku: List<Buku>): ListBukuUiState()
}

