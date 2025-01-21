package com.example.uas_pam.ui.viewModel.Anggota




data class InsertBukuUiState(
    val insertBukuUiEvent: InsertBukuUiEvent = InsertBukuUiEvent()
)

data class InsertBukuUiEvent(
    val idBuku: Int = 0,
    val judul: String = "",
    val penulis: String = "",
    val kategori: String = "",
    val status: String = ""
)