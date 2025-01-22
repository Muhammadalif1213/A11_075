package com.example.uas_pam.ui.viewModel.Anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Anggota
import com.example.uas_pam.repository.AnggotaRepository
import com.example.uas_pam.ui.viewModel.Buku.ListBukuUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class ListAnggotaUiState{
    object Loading: ListAnggotaUiState()
    object Error: ListAnggotaUiState()
    data class Success(val listAnggota: List<Anggota>): ListAnggotaUiState()
}

class ListAnggotaViewModel(
    private val anggotaRepo: AnggotaRepository
):ViewModel(){
    var anggotaUiState: ListAnggotaUiState by mutableStateOf(ListAnggotaUiState.Loading)
        private set

    init {
        getAnggota()
    }

    fun getAnggota(){
        viewModelScope.launch {
            anggotaUiState = ListAnggotaUiState.Loading
            anggotaUiState = try {
                ListAnggotaUiState.Success(anggotaRepo.getAnggota())
            } catch (e: IOException) {
                ListAnggotaUiState.Error
            } catch (e: HttpException) {
                ListAnggotaUiState.Error
            }
        }
    }

}