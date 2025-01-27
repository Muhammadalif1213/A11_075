package com.example.uas_pam.ui.View.Peminjaman

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiUpdatePeminjaman
import com.example.uas_pam.ui.viewModel.Peminjaman.InsertPeminjamanViewModel
import com.example.uas_pam.ui.viewModel.Peminjaman.UpdatePeminjamanViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun updatePeminjamanView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    updateViewModel: UpdatePeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModel: InsertPeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadBukuAndAnggotaOptions()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdatePeminjaman.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ){ innerPadding ->
        EntryBody(
            insertUiState = updateViewModel.updateUiState,
            onPeminjamanValueChange = updateViewModel::updatePeminjamanState,
            onSaveClick = {
                coroutineScope.launch {
                    updateViewModel.updatePeminjaman()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding),
            anggotaOptions = viewModel.anggotaOptions,
            bukuOptions = viewModel.bukuOptions
        )

    }
}