package com.example.uas_pam.ui.View.Peminjaman

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiListPeminjaman
import com.example.uas_pam.ui.viewModel.Peminjaman.ListPeminjamanUiState
import com.example.uas_pam.ui.viewModel.Peminjaman.ListPeminjamanViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeminjamanScreen(
    navigateToItemEntry: () -> Unit,
    navigateToPengembalianEntry: () -> Unit,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    NavigateBack: () -> Unit,
    viewModel: ListPeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var showFiltered by remember { mutableStateOf(true) }

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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val displayedPeminjaman = when (val state = viewModel.peminjamanUiState) {
                is ListPeminjamanUiState.Success -> {
                    // Filter default: hanya yang "Dipinjam"
                    state.listPeminjaman.filter { it.status == "Dipinjam" }
                }
                else -> emptyList()
            }
            Card(
                modifier = modifier
                    .background(Color(0xFF2196F3))
                    .padding(8.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = modifier.padding(8.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(text = "List Peminjaman Buku",
                        style = MaterialTheme.typography.titleLarge)
                }
            }
            Card(
                modifier = Modifier.padding(8.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ){
                PeminjamanTable(
                    listPeminjamanUiState = ListPeminjamanUiState.Success(displayedPeminjaman),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    navigateToDetail = navigateToDetail
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(
                    onClick = navigateToItemEntry,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Pinjam Buku",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Peminjaman")
                }

                Button(
                    onClick = navigateToPengembalianEntry,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Kembalikan Buku",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Pengembalian")
                }
            }
        }
    }
}


@Composable
fun PeminjamanTable(
    listPeminjamanUiState: ListPeminjamanUiState,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit // Menambahkan parameter navigasi
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
                    text = "Tidak ada peminjaman yang belum dikembalikan",
                    modifier = modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                // Scrollable column
                Column(
                    modifier = modifier
                        .fillMaxWidth()
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
                        HeaderCell(text = "Buku", modifier = Modifier.weight(2f))
                        Spacer(modifier = Modifier.weight(1f))
                        HeaderCell(text = "Anggota", modifier = Modifier.weight(2f))
                        Spacer(modifier = Modifier.weight(1f))
                        HeaderCell(text = "Tanggal Peminjaman", modifier = Modifier.weight(2f))
                        Spacer(modifier = Modifier.weight(1f))
                        HeaderCell(text = "Tanggal Pengembalian", modifier = Modifier.weight(2f))
                        Spacer(modifier = Modifier.weight(1f))
                        HeaderCell(text = "Status", modifier = Modifier.weight(2f))
                    }

                    // Table Rows
                    listPeminjamanUiState.listPeminjaman.forEach { peminjaman ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .padding(horizontal = 8.dp)
                                .clickable { // Membungkus Row dengan clickable
                                    navigateToDetail(peminjaman.idPeminjaman.toString()) // Aksi navigasi
                                },
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            BodyCell(text = peminjaman.idPeminjaman.toString(), modifier = Modifier.weight(2f))
                            Spacer(modifier = Modifier.weight(1f))
                            BodyCell(text = peminjaman.judulBuku, modifier = Modifier.weight(2f))
                            Spacer(modifier = Modifier.weight(1f))
                            BodyCell(text = peminjaman.namaAnggota, modifier = Modifier.weight(2f))
                            Spacer(modifier = Modifier.weight(1f))
                            BodyCell(text = peminjaman.tanggalPeminjaman, modifier = Modifier.weight(2f))
                            Spacer(modifier = Modifier.weight(1f))
                            BodyCell(text = peminjaman.tanggalPengembalian, modifier = Modifier.weight(2f))
                            Spacer(modifier = Modifier.weight(1f))
                            BodyCell(text = peminjaman.status, modifier = Modifier.weight(2f))
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