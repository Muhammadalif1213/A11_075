package com.example.uas_pam.ui.viewModel.Peminjaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.repository.PeminjamanRepository
import kotlinx.coroutines.launch

class UpdatePeminjamanViewModel(
    savedStateHandle: SavedStateHandle,
    private val pr: PeminjamanRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertPeminjamanUiState())
        private set
    val idPeminjaman: Int = checkNotNull(savedStateHandle["idPeminjaman"])

    init {
        viewModelScope.launch{
            updateUiState = pr.getPeminjamanById(idPeminjaman).toInsertUiStatePeminjaman()
        }
    }
    fun updatePeminjamanState(insertPeminjamanUiEvent: InsertPeminjamanUiEvent){
        updateUiState = InsertPeminjamanUiState(insertPeminjamanUiEvent = insertPeminjamanUiEvent)

    }

    suspend fun updatePeminjaman() {
        viewModelScope.launch {
            try {
                pr.editPeminjaman(
                    idPeminjaman,
                    updateUiState.insertPeminjamanUiEvent.toPeminjaman()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}