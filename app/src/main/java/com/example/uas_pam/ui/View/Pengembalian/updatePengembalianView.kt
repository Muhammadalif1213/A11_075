package com.example.uas_pam.ui.View.Pengembalian

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.viewModel.Pengembalian.InsertPengembalianViewModel
import com.example.uas_pam.ui.viewModel.Pengembalian.UpdatePengembalianViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun updatePengembalianScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    updateViewModel: UpdatePengembalianViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModel: InsertPengembalianViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onSavePengembalianClick: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadBukuOptions()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Update Pengembalian",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        EntryPengembalianBody(
            insertUiState = updateViewModel.updateUiState,
            onPengembalianValueChange = updateViewModel::updatePengembalianState,
            bukuOptionsPengembalian = viewModel.bukuOptions,
            onSavePengembalianClick = {
                coroutineScope.launch {
                    updateViewModel.updatePengembalian()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}
