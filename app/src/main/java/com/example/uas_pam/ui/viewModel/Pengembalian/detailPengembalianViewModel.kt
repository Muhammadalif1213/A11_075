package com.example.uas_pam.ui.viewModel.Pengembalian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Pengembalian
import com.example.uas_pam.repository.PengembalianRepository
import com.example.uas_pam.ui.Navigation.DestinasiDetailPengembalian
import com.example.uas_pam.ui.viewModel.Buku.ListBukuUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed class DetailPengembalianUiState{
    data class Success(val Pengembalian: Pengembalian): DetailPengembalianUiState()
    object Error: DetailPengembalianUiState()
    object Loading: DetailPengembalianUiState()
}

class DetailPengembalianViewModel(
    savedStateHandle: SavedStateHandle,
    private val pk: PengembalianRepository
): ViewModel() {
    private val idPengembalian: Int = checkNotNull(savedStateHandle[DestinasiDetailPengembalian.IDKEMBALI])
    var detailPengembalianUiState: DetailPengembalianUiState by mutableStateOf(
        DetailPengembalianUiState.Loading)
        private set

    init {
        getPengembalianById()
    }

    fun getPengembalianById() {
        viewModelScope.launch {
            detailPengembalianUiState = com.example.uas_pam.ui.viewModel.Pengembalian.DetailPengembalianUiState.Loading
            detailPengembalianUiState = try {
                DetailPengembalianUiState.Success(pk.getPengembalianById(idPengembalian))
            } catch (e: Exception) {
                DetailPengembalianUiState.Error
            }
        }
    }
    fun deletePengembalian(id_buku: Int){
        viewModelScope.launch {
            try {
                pk.deletePengembalian(id_buku)
            }catch (e: Exception){
                ListBukuUiState.Error
            }catch (e: HttpException) {
                ListBukuUiState.Error
            }
        }
    }
}