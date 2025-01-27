package com.example.uas_pam.ui.viewModel.Pengembalian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Pengembalian
import com.example.uas_pam.repository.BukuRepository
import com.example.uas_pam.repository.PeminjamanRepository
import com.example.uas_pam.repository.PengembalianRepository
import kotlinx.coroutines.launch

class InsertPengembalianViewModel(
    private val pm: PeminjamanRepository,
    private val pk: PengembalianRepository,
) : ViewModel() {

    var uiState by mutableStateOf(InsertPengembalianUiState())
        private set

    var bukuOptions by mutableStateOf(emptyList<BukuOptionPengembalian>())
        private set

    // Menambahkan state untuk menangani loading/error
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set


    fun loadBukuOptions() {
        viewModelScope.launch {
            isLoading = true
            try {
                // Muat daftar buku dan anggota dari repository
                bukuOptions = pm.getPeminjaman().map { BukuOptionPengembalian(it.idPeminjaman, it.judulBuku) }
                errorMessage = null
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = "Gagal memuat data"
            } finally {
                isLoading = false
            }
        }
    }

    fun updateInsertPengembalianState(insertUiEvent: InsertPengembalianUiEvent) {
        uiState = InsertPengembalianUiState(insertUiEvent)
    }



    suspend fun insertPengembalian() {
        try {
            pk.insertPengembalian(uiState.insertPengembalianUiEvent.toPengembalian())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

data class BukuOptionPengembalian(val idPeminjaman: Int, val judul: String)

fun Pengembalian.toInsertUiStatePengembalian(): InsertPengembalianUiState = InsertPengembalianUiState(
    insertPengembalianUiEvent = toInsertPeengembalianUievent()
)

fun Pengembalian.toInsertPeengembalianUievent(): InsertPengembalianUiEvent = InsertPengembalianUiEvent(
    idPengembalian = idPengembalian,
    idPeminjaman = idPeminjaman.toString(),
    judul = judul,
    nama = nama,
    tanggalDikembalikan = tanggalDikembalikan,
    denda = denda
)

fun InsertPengembalianUiEvent.toPengembalian(): Pengembalian = Pengembalian(
    idPengembalian = idPengembalian,
    idPeminjaman = idPeminjaman.toInt(),
    judul = judul,
    nama = nama,
    tanggalDikembalikan = tanggalDikembalikan,
    denda = denda
)

data class InsertPengembalianUiState(
    val insertPengembalianUiEvent: InsertPengembalianUiEvent = InsertPengembalianUiEvent(),
    val isLoading: Boolean = false, // Bisa digunakan untuk menampilkan loading state
    val errorMessage: String? = null // Menyimpan pesan error jika ada
)


data class InsertPengembalianUiEvent(
    val idPengembalian: Int = 0,
    val idPeminjaman: String = "",
    val judul: String = "",
    val nama: String = "",
    val tanggalDikembalikan: String = "",
    val denda: String = ""
)