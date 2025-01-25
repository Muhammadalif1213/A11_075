package com.example.uas_pam.ui.View.Peminjaman

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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiListPeminjaman
import com.example.uas_pam.ui.viewModel.Peminjaman.ListPeminjamanUiState
import com.example.uas_pam.ui.viewModel.Peminjaman.ListPeminjamanViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeminjamanScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    NavigateBack: () -> Unit,
    viewModel: ListPeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiListPeminjaman.titleRes,
                navigateUp = NavigateBack,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPeminjaman()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Peminjaman"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Tabel untuk menampilkan data peminjaman
            PeminjamanTable(
                listPeminjamanUiState = viewModel.peminjamanUiState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}


@Composable
fun PeminjamanTable(
    listPeminjamanUiState: ListPeminjamanUiState,
    modifier: Modifier = Modifier,
) {
    when (listPeminjamanUiState) {
        is ListPeminjamanUiState.Loading -> {
            Text(
                text = "Memuat data...",
                modifier = modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        is ListPeminjamanUiState.Success -> {
            if (listPeminjamanUiState.listPeminjaman.isEmpty()) {
                Text(
                    text = "Tidak ada data peminjaman",
                    modifier = modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                // Scrollable column
                Column(
                    modifier = modifier
                        .height(200.dp)
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
                        HeaderCell(text = "Id Buku", modifier = Modifier.weight(2f))
                        Spacer(modifier = Modifier.weight(1f))
                        HeaderCell(text = "Id_Anggota", modifier = Modifier.weight(2f))
                        Spacer(modifier = Modifier.weight(1f))
                        HeaderCell(text = "Tanggal Peminjaman", modifier = Modifier.weight(2f))
                        Spacer(modifier = Modifier.weight(1f))
                        HeaderCell(text = "Tanggal Pengembalian", modifier = Modifier.weight(2f))
                    }

                    // Table Rows
                    listPeminjamanUiState.listPeminjaman.forEach { peminjaman ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            BodyCell(text = peminjaman.idPeminjaman.toString(), modifier = Modifier.weight(2f))
                            Spacer(modifier = Modifier.weight(1f))
                            BodyCell(text = peminjaman.idBuku.toString(), modifier = Modifier.weight(2f))
                            Spacer(modifier = Modifier.weight(1f))
                            BodyCell(text = peminjaman.idAnggota.toString(), modifier = Modifier.weight(2f))
                            Spacer(modifier = Modifier.weight(1f))
                            BodyCell(text = peminjaman.tanggalPeminjaman, modifier = Modifier.weight(2f))
                            Spacer(modifier = Modifier.weight(1f))
                            BodyCell(text = peminjaman.tanggalPengembalian, modifier = Modifier.weight(2f))
                        }
                    }
                }
            }
        }
        is ListPeminjamanUiState.Error -> {
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