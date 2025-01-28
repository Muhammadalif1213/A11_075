package com.example.uas_pam.ui.View.Peminjaman

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        Column (
            modifier = Modifier.padding(innerPadding)
                .background(Color(0xFF2196F3))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Card(
                modifier = Modifier.padding(16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ){
                EntryBody(
                    insertUiState = updateViewModel.updateUiState,
                    onPeminjamanValueChange = updateViewModel::updatePeminjamanState,
                    onSaveClick = {
                        coroutineScope.launch {
                            updateViewModel.updatePeminjaman()
                            navigateBack()
                        }
                    },
                    modifier = Modifier,
                    anggotaOptions = viewModel.anggotaOptions,
                    bukuOptions = viewModel.bukuOptions
                )
            }
        }


    }
}