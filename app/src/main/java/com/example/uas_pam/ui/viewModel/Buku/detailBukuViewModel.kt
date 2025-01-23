package com.example.uas_pam.ui.viewModel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Buku
import com.example.uas_pam.repository.BukuRepository
import com.example.uas_pam.ui.Navigation.DestinasiDetailBuku
import kotlinx.coroutines.launch

sealed class DetailBukuUiState{
    data class Success(val buku: Buku): DetailBukuUiState()
    object Error: DetailBukuUiState()
    object Loading: DetailBukuUiState()
}

class DetailBukuViewModel(
    savedStateHandle: SavedStateHandle,
    private val bk: BukuRepository
): ViewModel() {
    private val idBuku: Int = checkNotNull(savedStateHandle[DestinasiDetailBuku.IDBUKU])
    var detailBukuUiState: DetailBukuUiState by mutableStateOf(DetailBukuUiState.Loading)
        private set

    init {
        getBukuById()
    }

    fun getBukuById() {
        viewModelScope.launch {
            detailBukuUiState = DetailBukuUiState.Loading
            detailBukuUiState = try {
                DetailBukuUiState.Success(bk.getBukuById(idBuku))
            } catch (e: Exception) {
                DetailBukuUiState.Error
            }
        }
    }
}
