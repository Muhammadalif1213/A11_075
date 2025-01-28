package com.example.uas_pam.ui.View.Anggota

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.R
import com.example.uas_pam.model.Anggota
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiListAnggota
import com.example.uas_pam.ui.viewModel.Anggota.ListAnggotaUiState
import com.example.uas_pam.ui.viewModel.Anggota.ListAnggotaViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAnggotaScreen(
    navigateToAnggotaEntry: () -> Unit,
    NavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: ListAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                navigateUp = NavigateUp,
                title = DestinasiListAnggota.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getAnggota()
                }

            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = navigateToAnggotaEntry,
                text = { Text("Tambah Anggota") },
                icon = { Icon(Icons.Default.Add, contentDescription = "Add Buku") },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    ){
            innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF2196F3))
        ){
            anggotaLayout(
                modifier = Modifier.padding(8.dp),
                onDetailClick = onDetailClick,
                ListAnggotaUiState = viewModel.anggotaUiState
            )
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(id = R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier.size(200.dp)
    )
}

@Composable
fun onErr(
    retryAction: () -> Unit,modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = null
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun anggotaLayout(
    modifier: Modifier,
    onDetailClick: (String) -> Unit,
    ListAnggotaUiState: ListAnggotaUiState
){
    Card (
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xFF42A5F5)),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = MaterialTheme.shapes.medium,
    ){
        AnggotaTable(
            listAnggotaUiState = ListAnggotaUiState,
            modifier = Modifier,
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun AnggotaTable(
    listAnggotaUiState: ListAnggotaUiState,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit
) {
    when (listAnggotaUiState) {
        is ListAnggotaUiState.Loading -> {
            Text(
                text = "Memuat data...",
                modifier = modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        is ListAnggotaUiState.Success -> {
            if (listAnggotaUiState.listAnggota.isEmpty()) {
                Text(
                    text = "Tidak ada data anggota",
                    modifier = modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                // Scrollable column
                Column(
                    modifier = modifier
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState()) // Enable vertical scroll
                ) {
                    // Table Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        HeaderCell(text = "ID", modifier = Modifier.weight(2f))
                        Spacer(modifier = Modifier.weight(1f))
                        HeaderCell(text = "Nama", modifier = Modifier.weight(2f))
                        Spacer(modifier = Modifier.weight(1f))
                        HeaderCell(text = "Email", modifier = Modifier.weight(2f))
                        Spacer(modifier = Modifier.weight(1f))
                        HeaderCell(text = "No HP", modifier = Modifier.weight(2f))
                    }

                    // Table Rows
                    listAnggotaUiState.listAnggota.forEach { anggota ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .padding(horizontal = 8.dp)
                                .clickable { onDetailClick(anggota.idAnggota.toString()) },
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            BodyCell(text = anggota.idAnggota.toString(), modifier = Modifier.weight(2f))
                            Spacer(modifier = Modifier.weight(1f))
                            BodyCell(text = anggota.nama, modifier = Modifier.weight(3f))
                            Spacer(modifier = Modifier.weight(1f))
                            BodyCell(text = anggota.email, modifier = Modifier.weight(3f))
                            Spacer(modifier = Modifier.weight(1f))
                            BodyCell(text = anggota.nomorTelepon, modifier = Modifier.weight(2f))
                        }
                    }
                }
            }
        }
        is ListAnggotaUiState.Error -> {
            Text(
                text = "Gagal memuat data. Coba lagi.",
                modifier = modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun HeaderCell(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier
            .padding(vertical = 8.dp),
        style = MaterialTheme.typography.labelLarge,
        maxLines = 1,
        textAlign = TextAlign.Center // Center text in header
    )
}

@Composable
fun BodyCell(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier
            .padding(vertical = 8.dp),
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 1,
        textAlign = TextAlign.Center // Center text in body
    )
}