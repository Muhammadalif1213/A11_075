package com.example.uas_pam.ui.viewModel.Pengembalian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.repository.PengembalianRepository
import kotlinx.coroutines.launch

class UpdatePengembalianViewModel(
    savedStateHandle: SavedStateHandle,
    private val pr: PengembalianRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertPengembalianUiState())
        private set
    val idPengembalian: Int = checkNotNull(savedStateHandle["idPengembalian"])

    init {
        viewModelScope.launch{
            updateUiState = pr.getPengembalianById(idPengembalian).toInsertUiStatePengembalian()
        }
    }
    fun updatePengembalianState(insertPengembalianUiEvent: InsertPengembalianUiEvent){
        updateUiState = InsertPengembalianUiState(insertPengembalianUiEvent = insertPengembalianUiEvent)

    }

    suspend fun updatePengembalian() {
        viewModelScope.launch {
            try {
                pr.editPengembalian(
                    idPengembalian,
                    updateUiState.insertPengembalianUiEvent.toPengembalian()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}