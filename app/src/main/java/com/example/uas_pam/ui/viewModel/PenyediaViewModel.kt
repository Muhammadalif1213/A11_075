package com.example.uas_pam.ui.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uas_pam.PerpusApplication
import com.example.uas_pam.ui.viewModel.Buku.InsertBukuViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {

    }
}

fun CreationExtras.perpusApp(): PerpusApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as PerpusApplication)