package com.example.uas_pam.ui.viewModel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Buku
import com.example.uas_pam.repository.BukuRepository
import kotlinx.coroutines.launch

class InsertBukuViewModel(private val bk: BukuRepository): ViewModel() {
    var uiState by mutableStateOf(InsertBukuUiState())
        private set

    fun updateInsertBukuState(insertUiEvent: InsertBukuUiEvent){
        uiState = InsertBukuUiState(insertUiEvent)
    }

    suspend fun insertBuku(){
        viewModelScope.launch {
            try {
                bk.insertBuku(uiState.insertBukuUiEvent.toBuku())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

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