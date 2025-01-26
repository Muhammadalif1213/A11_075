package com.example.uas_pam.ui.viewModel.Pengembalian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Pengembalian
import com.example.uas_pam.repository.PengembalianRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class ListPengembalianUiState{
    object Loading: ListPengembalianUiState()
    object Error: ListPengembalianUiState()
    data class Success(val listPengembalian: List<Pengembalian>): ListPengembalianUiState()
}

class ListPengembalianViewModel(
    private val pengembalianRepo: PengembalianRepository
): ViewModel() {
    var pengembalianUiState: ListPengembalianUiState by mutableStateOf(ListPengembalianUiState.Loading)
        private set

    init {
        getPengembalian()
    }

    fun getPengembalian() {
        viewModelScope.launch {
            pengembalianUiState = ListPengembalianUiState.Loading
            pengembalianUiState = try {
                ListPengembalianUiState.Success(pengembalianRepo.getPengembalian())
            } catch (e: IOException) {
                ListPengembalianUiState.Error
            } catch (e: HttpException) {
                ListPengembalianUiState.Error
            }
        }
    }
}