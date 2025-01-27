package com.example.uas_pam.ui.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uas_pam.PerpusApplication
import com.example.uas_pam.ui.viewModel.Anggota.DetailAnggotaViewModel
import com.example.uas_pam.ui.viewModel.Anggota.InsertAnggotaViewModel
import com.example.uas_pam.ui.viewModel.Anggota.ListAnggotaViewModel
import com.example.uas_pam.ui.viewModel.Anggota.UpdateAnggotaViewModel
import com.example.uas_pam.ui.viewModel.Buku.DetailBukuViewModel
import com.example.uas_pam.ui.viewModel.Buku.InsertBukuViewModel
import com.example.uas_pam.ui.viewModel.Buku.ListBukuViewModel
import com.example.uas_pam.ui.viewModel.Buku.UpdateBukuViewModel
import com.example.uas_pam.ui.viewModel.Peminjaman.DetailPeminjamanViewModel
import com.example.uas_pam.ui.viewModel.Peminjaman.InsertPeminjamanViewModel
import com.example.uas_pam.ui.viewModel.Peminjaman.ListPeminjamanViewModel
import com.example.uas_pam.ui.viewModel.Peminjaman.UpdatePeminjamanViewModel
import com.example.uas_pam.ui.viewModel.Pengembalian.DetailPengembalianViewModel
import com.example.uas_pam.ui.viewModel.Pengembalian.InsertPengembalianViewModel
import com.example.uas_pam.ui.viewModel.Pengembalian.ListPengembalianViewModel
import com.example.uas_pam.ui.viewModel.Pengembalian.UpdatePengembalianViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            InsertBukuViewModel(perpusApp().container.bukuRepository)
        }
        initializer {
            HomeViewModel()
        }
        initializer {
            ListBukuViewModel(perpusApp().container.bukuRepository)
        }
        initializer {
            InsertAnggotaViewModel(perpusApp().container.anggotaRepository)
        }
        initializer {
            ListAnggotaViewModel(perpusApp().container.anggotaRepository)
        }
        initializer {
            DetailBukuViewModel(
                createSavedStateHandle(),
                perpusApp().container.bukuRepository
            )
        }
        initializer {
            UpdateBukuViewModel(
                createSavedStateHandle(),
                perpusApp().container.bukuRepository
            )
        }
        initializer {
            DetailAnggotaViewModel(
                createSavedStateHandle(),
                perpusApp().container.anggotaRepository
            )
        }
        initializer {
            UpdateAnggotaViewModel(
                createSavedStateHandle(),
                perpusApp().container.anggotaRepository
            )
        }
        initializer {
            ListPeminjamanViewModel(
                perpusApp().container.peminjamanRepository)
        }
        initializer {
            InsertAnggotaViewModel(perpusApp().container.anggotaRepository)
        }
        initializer {
            InsertPeminjamanViewModel(
                perpusApp().container.peminjamanRepository,
                perpusApp().container.bukuRepository,
                perpusApp().container.anggotaRepository)
        }
        initializer {
            DetailPeminjamanViewModel(
                createSavedStateHandle(),
                perpusApp().container.peminjamanRepository
            )
        }
        initializer {
            UpdatePeminjamanViewModel(
                createSavedStateHandle(),
                perpusApp().container.peminjamanRepository
            )
        }

        initializer {
            ListPengembalianViewModel(
                perpusApp().container.pengembalianRepository
            )
        }

        initializer {
            InsertPengembalianViewModel(
                perpusApp().container.peminjamanRepository,
                perpusApp().container.pengembalianRepository,
            )
        }
        initializer {
            DetailPengembalianViewModel(
                createSavedStateHandle(),
                perpusApp().container.pengembalianRepository
            )
        }
        initializer {
            UpdatePengembalianViewModel(
                createSavedStateHandle(),
                perpusApp().container.pengembalianRepository
            )
        }
    }
}

fun CreationExtras.perpusApp(): PerpusApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as PerpusApplication)