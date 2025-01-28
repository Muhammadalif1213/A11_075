package com.example.uas_pam.ui.View.Buku

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
import com.example.uas_pam.model.Anggota
import com.example.uas_pam.model.Buku
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiDetailBuku
import com.example.uas_pam.ui.viewModel.Buku.DetailBukuUiState
import com.example.uas_pam.ui.viewModel.Buku.DetailBukuViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    modifier: Modifier = Modifier,
    detailViewModel: DetailBukuViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailBuku.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    detailViewModel.getBukuById()
                }
            )
        }
    ) { innerPadding ->
        StatusDetail(
            detailUiState = detailViewModel.detailBukuUiState,
            retryAction = { detailViewModel.getBukuById() },
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            onEditClick = onEditClick,
            onDeleteClick = {
                detailViewModel.deleteBuku(it.idBuku)
                navigateBack()
            }
        )
    }
}

@Composable
fun StatusDetail(
    detailUiState: DetailBukuUiState,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    retryAction: () -> Unit,
    onDeleteClick: (Buku) -> Unit = {}
) {
    when (detailUiState) {
        is DetailBukuUiState.Success -> {
            DetailBukuLayout(
                buku = detailUiState.buku,
                onEditClick = { onEditClick(it) },
                modifier = modifier,
                onDeleteClick = {
                    onDeleteClick(it)
                }
            )
        }
        is DetailBukuUiState.Loading -> OnLoading(modifier = Modifier)
        is DetailBukuUiState.Error -> onErr(retryAction, modifier = Modifier)
    }
}

@Composable
fun DetailBukuLayout(
    modifier: Modifier = Modifier,
    buku: Buku,
    onEditClick: (String) -> Unit = {},
    onDeleteClick: (Buku) -> Unit = {}
) {
    var deleteConfifrmationRequired by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        ItemDetailBuku(
            buku = buku,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onEditClick(buku.idBuku.toString())
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
        DeleteAnggotaConfirmationDialog(
            onDeleteConfirm = {
                deleteConfifrmationRequired = false
                onDeleteClick(buku)
            },
            onDeleteCancel = {deleteConfifrmationRequired = false},
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ItemDetailBuku(
    modifier: Modifier = Modifier,
    buku: Buku
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
            ComponentDetailBuku(judul = "Id Buku", isinya = buku.idBuku.toString())
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBuku(judul = "Judul Buku", isinya = buku.judul)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBuku(judul = "Penulis", isinya = buku.penulis)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBuku(judul = "Kategori", isinya = buku.kategori)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBuku(judul = "Status", isinya = buku.status)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ComponentDetailBuku(
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
private fun DeleteAnggotaConfirmationDialog(
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