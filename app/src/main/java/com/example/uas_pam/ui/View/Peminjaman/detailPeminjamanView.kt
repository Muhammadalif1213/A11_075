package com.example.uas_pam.ui.View.Peminjaman

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.model.Peminjaman
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiDetailPeminjaman
import com.example.uas_pam.ui.viewModel.Peminjaman.DetailPeminjamanUiState
import com.example.uas_pam.ui.viewModel.Peminjaman.DetailPeminjamanViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewPeminjaman(
    modifier: Modifier = Modifier,
    detailViewModel: DetailPeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailPeminjaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    detailViewModel.getPeminjamanById()
                }
            )
        }
    ) { innerPadding ->
        StatusDetail(
            detailUiState = detailViewModel.detailPeminjamanUiState,
            retryAction = { detailViewModel.getPeminjamanById() },
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            onEditClick = onEditClick
        )
    }
}

@Composable
fun StatusDetail(
    detailUiState: DetailPeminjamanUiState,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    retryAction: () -> Unit,
) {
    when (detailUiState) {
        is DetailPeminjamanUiState.Success -> {
            DetailPeminjamanLayout(
                peminjaman = detailUiState.peminjaman,
                onEditClick = { onEditClick(it) },
                modifier = modifier
            )
        }
        is DetailPeminjamanUiState.Loading -> OnLoading(modifier = Modifier)
        is DetailPeminjamanUiState.Error -> onErr(retryAction, modifier = Modifier)
    }
}

@Composable
fun DetailPeminjamanLayout(
    modifier: Modifier = Modifier,
    peminjaman: Peminjaman,
    onEditClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        ItemDetailPeminjaman(
            peminjaman = peminjaman,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onEditClick(peminjaman.idPeminjaman.toString())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(48.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Update",
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ItemDetailPeminjaman(
    modifier: Modifier = Modifier,
    peminjaman: Peminjaman
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailPeminjaman(judul = "Id Peminjaman", isinya = peminjaman.idPeminjaman.toString())
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPeminjaman(judul = "Id Buku", isinya = peminjaman.judulBuku)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPeminjaman(judul = "Id Anggota", isinya = peminjaman.namaAnggota)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPeminjaman(judul = "Tanggal Peminjaman", isinya = peminjaman.tanggalPeminjaman)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPeminjaman(judul = "Tanggal Pengembalian", isinya = peminjaman.tanggalPengembalian)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPeminjaman(judul = "Status", isinya = peminjaman.status)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ComponentDetailPeminjaman(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul: ",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}