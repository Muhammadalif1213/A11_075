package com.example.uas_pam.ui.View.Anggota

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .background(Color(0xFF2196F3))
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Card(
                modifier = modifier
                    .background(Color(0xFF2196F3))
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ){
                EntryBody(
                    insertUiState = updateViewModel.updateUiState,
                    onAnggotaValueChange = updateViewModel::updateAnggotaState,
                    onSaveClick = {
                        coroutineScope.launch {
                            updateViewModel.updateAnggota()
                            navigateBack()
                        }
                    },
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }
        }

    }
}