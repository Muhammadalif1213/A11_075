package com.example.uas_pam.ui.viewModel.Peminjaman

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Peminjaman
import com.example.uas_pam.repository.PeminjamanRepository
import kotlinx.coroutines.launch


class InsertPeminjamanViewModel(private val pm: PeminjamanRepository): ViewModel() {
    var uiState by mutableStateOf(InsertPeminjamanUiState())
        private set

    fun updateInsertPeminjamanState(insertUiEvent: InsertPeminjamanUiEvent){
        uiState = InsertPeminjamanUiState(insertUiEvent)
    }

    suspend fun insertPeminjaman(){
        viewModelScope.launch {
            try {
                pm.insertPeminjaman(uiState.insertPeminjamanUiEvent.toPeminjaman())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

fun InsertPeminjamanUiEvent.toPeminjaman(): Peminjaman = Peminjaman(
    idPeminjaman = idPeminjaman,
    idBuku = idBuku.toInt(),
    idAnggota = idAnggota.toInt(),
    tanggalPeminjaman = tanggalPeminjaman,
    tanggalPengembalian = tanggalPengembalian
)

fun Peminjaman.toInsertUiStatePeminjaman(): InsertPeminjamanUiState = InsertPeminjamanUiState(
    insertPeminjamanUiEvent = toInsertPeminjamanUiEvent()
)

fun Peminjaman.toInsertPeminjamanUiEvent(): InsertPeminjamanUiEvent = InsertPeminjamanUiEvent(
    idPeminjaman = idPeminjaman,
    idBuku = idBuku.toString(),
    idAnggota = idAnggota.toString(),
    tanggalPeminjaman = tanggalPeminjaman,
    tanggalPengembalian = tanggalPengembalian
)

data class InsertPeminjamanUiState(
    val insertPeminjamanUiEvent: InsertPeminjamanUiEvent = InsertPeminjamanUiEvent()
)

data class InsertPeminjamanUiEvent(
    val idPeminjaman: Int = 0,
    val idBuku: String = "",
    val idAnggota: String = "",
    val tanggalPeminjaman: String = "",
    val tanggalPengembalian: String = ""
)

