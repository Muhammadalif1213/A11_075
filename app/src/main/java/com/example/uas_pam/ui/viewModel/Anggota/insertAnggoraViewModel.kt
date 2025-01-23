package com.example.uas_pam.ui.viewModel.Anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Anggota
import com.example.uas_pam.repository.AnggotaRepository
import kotlinx.coroutines.launch


class InsertAnggotaViewModel(private val ang: AnggotaRepository): ViewModel() {
    var uiState by mutableStateOf(InsertAnggotaUiState())
        private set

    fun updateInsertAnggotaState(insertUiEvent: InsertAnggotaUiEvent){
        uiState = InsertAnggotaUiState(insertUiEvent)
    }

    suspend fun insertAnggota(){
        viewModelScope.launch {
            try {
                ang.insertAnggota(uiState.insertAnggotaUiEvent.toAnggota())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}


fun InsertAnggotaUiEvent.toAnggota(): Anggota = Anggota(
    idAnggota = idAnggota,
    nama = nama,
    email = email,
    nomorTelepon = nomorTelepon
)

fun Anggota.toInsertUiStateAnggota(): InsertAnggotaUiState = InsertAnggotaUiState(
    insertAnggotaUiEvent = toInsertAnggotaUiEvent()
)

fun Anggota.toInsertAnggotaUiEvent(): InsertAnggotaUiEvent = InsertAnggotaUiEvent(
    idAnggota = idAnggota,
    nama = nama,
    email = email,
    nomorTelepon = nomorTelepon
)

data class InsertAnggotaUiState(
    val insertAnggotaUiEvent: InsertAnggotaUiEvent = InsertAnggotaUiEvent()
)


data class InsertAnggotaUiEvent(
    val idAnggota: Int = 0,
    val nama: String = "",
    val email: String = "",
    val nomorTelepon: String = "",
)