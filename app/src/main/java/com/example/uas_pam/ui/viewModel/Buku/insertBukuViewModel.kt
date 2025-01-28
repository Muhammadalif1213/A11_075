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

    private fun validateInput(): Boolean{
        val event = uiState.insertBukuUiEvent
        val errorState = FormErrorState(
            judul = if (event.judul.isNotEmpty()) null else " Judul tidak boleh kosong ",
            penulis = if (event.penulis.isNotEmpty()) null else " Penulis tidak boleh kosong ",
            kategori = if (event.kategori.isNotEmpty()) null else " Kategori tidak boleh kosong ",
            status = if (event.status.isNotEmpty()) null else " Status tidak boleh kosong "
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertBuku(){

        val currentEvent = uiState.insertBukuUiEvent

        if (validateInput()){
            viewModelScope.launch {
                try {
                    bk.insertBuku(currentEvent.toBuku())
                    uiState = uiState.copy(
                        snackBarMessage =  "Data Buku Berhasil disimpan",
                        insertBukuUiEvent = InsertBukuUiEvent(),
                        isEntryValid = FormErrorState()
                    )
                }catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data Buku Gagal disimpan"
                    )
                }
            }
        }else{
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda."
            )
        }

    }

}

data class FormErrorState(
    val judul: String? = null,
    val penulis: String? = null,
    val kategori: String? = null,
    val status: String? = null
){
    fun isValid(): Boolean{
        return judul == null && penulis == null && kategori == null && status == null
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
    val insertBukuUiEvent: InsertBukuUiEvent = InsertBukuUiEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null
)

data class InsertBukuUiEvent(
    val idBuku: Int = 0,
    val judul: String = "",
    val penulis: String = "",
    val kategori: String = "",
    val status: String = ""
)