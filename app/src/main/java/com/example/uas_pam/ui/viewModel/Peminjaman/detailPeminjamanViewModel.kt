package com.example.uas_pam.ui.viewModel.Peminjaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Peminjaman
import com.example.uas_pam.repository.PeminjamanRepository
import com.example.uas_pam.ui.Navigation.DestinasiDetailPeminjaman
import kotlinx.coroutines.launch

sealed class DetailPeminjamanUiState{
    data class Success(val peminjaman: Peminjaman): DetailPeminjamanUiState()
    object Error: DetailPeminjamanUiState()
    object Loading: DetailPeminjamanUiState()
}

class DetailPeminjamanViewModel(
    savedStateHandle: SavedStateHandle,
    private val pm: PeminjamanRepository
): ViewModel() {
    private val idPeminjaman: Int = checkNotNull(savedStateHandle[DestinasiDetailPeminjaman.IDPINJAM])
    var detailPeminjamanUiState:DetailPeminjamanUiState by mutableStateOf(
        DetailPeminjamanUiState.Loading)
        private set

    init {
        getPeminjamanById()
    }

    fun getPeminjamanById() {
        viewModelScope.launch {
            detailPeminjamanUiState = DetailPeminjamanUiState.Loading
            detailPeminjamanUiState = try {
                DetailPeminjamanUiState.Success(pm.getPeminjamanById(idPeminjaman))
            } catch (e: Exception) {
                DetailPeminjamanUiState.Error
            }
        }
    }
}
