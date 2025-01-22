package com.example.uas_pam.ui.viewModel.Buku


import com.example.uas_pam.model.Buku



fun InsertBukuUiEvent.toBuku(): Buku = Buku(
    idBuku = idBuku,
    judul = judul,
    penulis = penulis,
    kategori = kategori,
    status = status
)

fun Buku.toInsertUiStateBuku(): InsertBukuUiState = InsertBukuUiState(
    insertBukuUiEvent = toInsertBukuUiEvent()
)

fun Buku.toInsertBukuUiEvent(): InsertBukuUiEvent = InsertBukuUiEvent(
    idBuku = idBuku,
    judul = judul,
    penulis = penulis,
    kategori = kategori,
    status = status
)

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