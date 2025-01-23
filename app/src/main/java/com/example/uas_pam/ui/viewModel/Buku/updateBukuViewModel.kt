package com.example.uas_pam.ui.viewModel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.repository.BukuRepository
import kotlinx.coroutines.launch

class UpdateBukuViewModel(
    savedStateHandle: SavedStateHandle,
    private val bk: BukuRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertBukuUiState())
        private set
    val idBuku: Int = checkNotNull(savedStateHandle["idBuku"])

    init {
        viewModelScope.launch{
            updateUiState = bk.getBukuById(idBuku).toInsertUiStateBuku()
        }
    }

    fun updateBukuState(insertBukuUiEvent: InsertBukuUiEvent){
        updateUiState = InsertBukuUiState(insertBukuUiEvent = insertBukuUiEvent)
    }

    suspend fun updateBuku(){
        viewModelScope.launch {
            try {
                bk.editBuku(idBuku,updateUiState.insertBukuUiEvent.toBuku())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}