package com.example.uas_pam.ui.viewModel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Buku
import com.example.uas_pam.repository.BukuRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class ListBukuUiState{
    object Loading: ListBukuUiState()
    object Error: ListBukuUiState()
    data class Success(val listBuku: List<Buku>): ListBukuUiState()
}

class ListBukuViewModel(
    private val bukuRepo: BukuRepository
):ViewModel() {
    var bukuUiState: ListBukuUiState by mutableStateOf(ListBukuUiState.Loading)
        private set

    init {
        getBuku()
    }

    fun getBuku() {
        viewModelScope.launch {
            bukuUiState = ListBukuUiState.Loading
            bukuUiState = try {
                ListBukuUiState.Success(bukuRepo.getBuku())
            } catch (e: IOException) {
                ListBukuUiState.Error
            } catch (e: HttpException) {
                ListBukuUiState.Error
            }
        }
    }

}
