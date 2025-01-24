package com.example.uas_pam.ui.viewModel.Anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Anggota
import com.example.uas_pam.repository.AnggotaRepository
import com.example.uas_pam.ui.Navigation.DestinasiDetailAnggota
import kotlinx.coroutines.launch

sealed class DetailAnggotaUiState{
    data class Success(val anggota: Anggota): DetailAnggotaUiState()
    object Error: DetailAnggotaUiState()
    object Loading: DetailAnggotaUiState()
}

class DetailAnggotaViewModel(
    savedStateHandle: SavedStateHandle,
    private val ang: AnggotaRepository
): ViewModel() {
    private val idAnggota: Int = checkNotNull(savedStateHandle[DestinasiDetailAnggota.IDANGGOTA])
    var detailAnggotaUiState: DetailAnggotaUiState by mutableStateOf(DetailAnggotaUiState.Loading)
        private set

    init {
        getAnggotaById()
    }

    fun getAnggotaById() {
        viewModelScope.launch {
            detailAnggotaUiState = DetailAnggotaUiState.Loading
            detailAnggotaUiState = try {
                DetailAnggotaUiState.Success(ang.getAnggotaById(idAnggota))
            } catch (e: Exception) {
                DetailAnggotaUiState.Error
            }
        }
    }
}