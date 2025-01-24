package com.example.uas_pam.ui.viewModel.Anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.repository.AnggotaRepository
import kotlinx.coroutines.launch

class UpdateAnggotaViewModel(
    savedStateHandle: SavedStateHandle,
    private val ang: AnggotaRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertAnggotaUiState())
        private set
    val idAnggota: Int = checkNotNull(savedStateHandle["idAnggota"])

    init {
        viewModelScope.launch{
            updateUiState = ang.getAnggotaById(idAnggota).toInsertUiStateAnggota()
        }
    }

    fun updateAnggotaState(insertAnggotaUiEvent: InsertAnggotaUiEvent){
        updateUiState = InsertAnggotaUiState(insertAnggotaUiEvent = insertAnggotaUiEvent)
    }

    suspend fun updateAnggota(){
        viewModelScope.launch {
            try {
                ang.editAnggota(idAnggota,updateUiState.insertAnggotaUiEvent.toAnggota())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}