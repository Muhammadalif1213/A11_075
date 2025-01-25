package com.example.uas_pam.ui.viewModel.Peminjaman

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.model.Peminjaman
import com.example.uas_pam.repository.PeminjamanRepository
import com.example.uas_pam.ui.viewModel.Anggota.ListAnggotaViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class ListPeminjamanUiState{
    object Loading: ListPeminjamanUiState()
    object Error: ListPeminjamanUiState()
    data class Success(val listPeminjaman: List<Peminjaman>): ListPeminjamanUiState()
}

class ListPeminjamanViewModel(
    private val peminjamanRepo: PeminjamanRepository
): ViewModel() {
    var peminjamanUiState: ListPeminjamanUiState by mutableStateOf(ListPeminjamanUiState.Loading)
        private set

    init {
        getPeminjaman()
    }

    fun getPeminjaman() {
        viewModelScope.launch {
            peminjamanUiState = ListPeminjamanUiState.Loading
            peminjamanUiState = try {
                ListPeminjamanUiState.Success(peminjamanRepo.getPeminjaman())
            } catch (e: IOException) {
                ListPeminjamanUiState.Error
            } catch (e: HttpException) {
                ListPeminjamanUiState.Error
            }
        }
    }
}
