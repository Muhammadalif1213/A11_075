package com.example.uas_pam.ui.viewModel.Peminjaman

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.model.Peminjaman
import com.example.uas_pam.repository.AnggotaRepository
import com.example.uas_pam.repository.BukuRepository
import com.example.uas_pam.repository.PeminjamanRepository
import kotlinx.coroutines.launch


class InsertPeminjamanViewModel(
    private val pm: PeminjamanRepository,
    private val pb: BukuRepository,
    private val pa: AnggotaRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertPeminjamanUiState())
        private set

    var bukuOptions by mutableStateOf(emptyList<BukuOption>())
        private set

    var anggotaOptions by mutableStateOf(emptyList<AnggotaOption>())
        private set

    // Menambahkan state untuk menangani loading/error
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadBukuAndAnggotaOptions() {
        viewModelScope.launch {
            isLoading = true
            try {
                // Muat daftar buku dan filter hanya buku yang tersedia
                bukuOptions = pb.getBuku()
                    .filter { it.status == "Tersedia" }
                    .map { BukuOption(it.idBuku, it.judul) }

                // Muat daftar anggota tanpa filter tambahan
                anggotaOptions = pa.getAnggota()
                    .map { AnggotaOption(it.idAnggota, it.nama) }

                errorMessage = null
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = "Gagal memuat data"
            } finally {
                isLoading = false
            }
        }
    }


    fun updateInsertPeminjamanState(insertUiEvent: InsertPeminjamanUiEvent) {
        uiState = InsertPeminjamanUiState(insertUiEvent)
    }

    suspend fun insertPeminjaman() {
        try {
            pm.insertPeminjaman(uiState.insertPeminjamanUiEvent.toPeminjaman())
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = "Gagal melakukan peminjaman"
        }
    }
}

data class BukuOption(val id: Int, val judul: String)
data class AnggotaOption(val id: Int, val nama: String)

fun Peminjaman.toInsertUiStatePeminjaman(): InsertPeminjamanUiState = InsertPeminjamanUiState(
    insertPeminjamanUiEvent = toInsertPeminjamanUiEvent()

)

fun Peminjaman.toInsertPeminjamanUiEvent(): InsertPeminjamanUiEvent = InsertPeminjamanUiEvent(
    idPeminjaman = idPeminjaman,
    idBuku = idBuku.toString(),
    judulBuku = judulBuku,
    idAnggota = idAnggota.toString(),
    namaAnggota = namaAnggota,
    tanggalPeminjaman = tanggalPeminjaman,
    tanggalPengembalian = tanggalPengembalian,
    status = status
)


fun InsertPeminjamanUiEvent.toPeminjaman(): Peminjaman = Peminjaman(
    idPeminjaman = idPeminjaman,
    judulBuku = judulBuku,
    namaAnggota = namaAnggota,
    tanggalPeminjaman = tanggalPeminjaman,
    tanggalPengembalian = tanggalPengembalian,
    status = status,
    idBuku = idBuku.toInt(),
    idAnggota = idAnggota.toInt()
)

data class InsertPeminjamanUiState(
    val insertPeminjamanUiEvent: InsertPeminjamanUiEvent = InsertPeminjamanUiEvent(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class InsertPeminjamanUiEvent(
    val idPeminjaman: Int = 0,
    val idBuku: String = "",
    val judulBuku: String = "",
    val idAnggota: String = "",
    val namaAnggota: String = "",
    val tanggalPeminjaman: String = "",
    val tanggalPengembalian: String = "",
    val status: String = ""
)
