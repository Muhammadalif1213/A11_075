package com.example.uas_pam.ui.View.Anggota

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiUpdateAnggota
import com.example.uas_pam.ui.viewModel.Anggota.UpdateAnggotaViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun updateAnggotaView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    updateViewModel: UpdateAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory)
)
{
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdateAnggota.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) {innerPadding ->
        EntryBody(
            insertUiState = updateViewModel.updateUiState,
            onAnggotaValueChange = updateViewModel::updateAnggotaState,
            onSaveClick = {
                coroutineScope.launch {
                    updateViewModel.updateAnggota()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}