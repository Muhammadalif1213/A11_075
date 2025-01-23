package com.example.uas_pam.ui.View.Buku

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiUpdateBuku
import com.example.uas_pam.ui.viewModel.Buku.DetailBukuViewModel
import com.example.uas_pam.ui.viewModel.Buku.UpdateBukuViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun updateBukuView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    updateViewModel: UpdateBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
)
{
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdateBuku.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) {innerPadding ->
        EntryBody(
            insertUiState = updateViewModel.updateUiState,
            onBukuValueChange = updateViewModel::updateBukuState,
            onSaveClick = {
                coroutineScope.launch {
                    updateViewModel.updateBuku()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}