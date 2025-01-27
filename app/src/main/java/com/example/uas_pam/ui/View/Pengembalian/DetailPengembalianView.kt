package com.example.uas_pam.ui.View.Pengembalian

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.model.Pengembalian
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiDetailPengembalian
import com.example.uas_pam.ui.viewModel.Pengembalian.DetailPengembalianUiState
import com.example.uas_pam.ui.viewModel.Pengembalian.DetailPengembalianViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewPengembalian(
    modifier: Modifier = Modifier,
    detailViewModel: DetailPengembalianViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailPengembalian.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    detailViewModel.getPengembalianById()
                }
            )
        }
    ) { innerPadding ->
        StatusDetail(
            detailUiState = detailViewModel.detailPengembalianUiState,
            retryAction = { detailViewModel.getPengembalianById() },
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            onEditClick = onEditClick,
            onDeleteClick = {
                detailViewModel.deletePengembalian(it.idPengembalian)
                navigateBack()
            }

        )
    }
}

@Composable
fun StatusDetail(
    detailUiState: DetailPengembalianUiState,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    retryAction: () -> Unit,
    onDeleteClick: (Pengembalian) -> Unit = {}
) {
    when (detailUiState) {
        is DetailPengembalianUiState.Success -> {
            DetailPengembalianLayout(
                pengembalian = detailUiState.Pengembalian,
                onEditClick = { onEditClick(it) },
                modifier = modifier,
                onDeleteClick = {
                    onDeleteClick(it)
                }
            )
        }
        is DetailPengembalianUiState.Loading -> com.example.uas_pam.ui.View.Pengembalian.OnLoading(
            modifier = Modifier
        )
        is DetailPengembalianUiState.Error -> com.example.uas_pam.ui.View.Pengembalian.onErr(
            retryAction,
            modifier = Modifier
        )
    }
}

@Composable
fun DetailPengembalianLayout(
    modifier: Modifier = Modifier,
    pengembalian: Pengembalian,
    onEditClick: (String) -> Unit = {},
    onDeleteClick: (Pengembalian) -> Unit = {}
) {
    var deleteConfifrmationRequired by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        ItemDetailPengembalian(
            pengembalian = pengembalian,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onEditClick(pengembalian.idPengembalian.toString())
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
        Button(
            onClick = {
                deleteConfifrmationRequired = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(48.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red, // Mengatur warna tombol menjadi merah
                contentColor = Color.White  // Warna teks di dalam tombol tetap putih
            )
        ) {
            Text(
                text = "Delete",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
    if (deleteConfifrmationRequired){
        DeletePengembalianConfirmationDialog(
            onDeleteConfirm = {
                deleteConfifrmationRequired = false
                onDeleteClick(pengembalian)
            },
            onDeleteCancel = {deleteConfifrmationRequired = false},
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ItemDetailPengembalian(
    modifier: Modifier = Modifier,
    pengembalian: Pengembalian
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
            ComponentDetailPengembalian(judul = "Id Pengembalian", isinya = pengembalian.idPengembalian.toString())
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPengembalian(judul = "Id Buku", isinya = pengembalian.idPeminjaman.toString())
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPengembalian(judul = "Id Anggota", isinya = pengembalian.nama)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPengembalian(judul = "Tanggal Pengembalian", isinya = pengembalian.judul)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPengembalian(judul = "Tanggal Pengembalian", isinya = pengembalian.tanggalDikembalikan)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ComponentDetailPengembalian(
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

@Composable
private fun DeletePengembalianConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(onDismissRequest = { /* Do Nothing */},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data? ") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}