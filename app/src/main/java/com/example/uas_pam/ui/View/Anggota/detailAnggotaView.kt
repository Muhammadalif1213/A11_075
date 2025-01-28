package com.example.uas_pam.ui.View.Anggota

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.model.Anggota
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiDetailAnggota
import com.example.uas_pam.ui.viewModel.Anggota.DetailAnggotaUiState
import com.example.uas_pam.ui.viewModel.Anggota.DetailAnggotaViewModel
import com.example.uas_pam.ui.viewModel.Anggota.ListAnggotaViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAnggotaView(
    modifier: Modifier = Modifier,
    detailViewModel: DetailAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit,
    navigateBack: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: ListAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailAnggota.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    detailViewModel.getAnggotaById()
                }
            )
        }
    ){
            innerPadding ->
        StatusDetail(
            detailUiState = detailViewModel.detailAnggotaUiState,
            retryAction = {detailViewModel.getAnggotaById()},
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            onEditClick = onEditClick,
            onDeleteClick = {
                viewModel.deleteAnggota(it.idAnggota)
                onBackClick()
            }
        )
    }
}

@Composable
fun StatusDetail(
    detailUiState: DetailAnggotaUiState,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    retryAction: () -> Unit,
    onDeleteClick: (Anggota) -> Unit = {}

){
    when(detailUiState) {
        is DetailAnggotaUiState.Success ->{
            DetailAnggotaLayout(
                anggota = detailUiState.anggota,
                onEditClick = {onEditClick(it)},
                modifier = modifier,
                onDeleteClick = {
                    onDeleteClick(it)
                }
            )
        }
        is DetailAnggotaUiState.Loading -> OnLoading(modifier = Modifier)
        is DetailAnggotaUiState.Error -> onErr(
            retryAction,
            modifier = Modifier
        )
    }
}

@Composable
fun DetailAnggotaLayout(
    modifier: Modifier = Modifier,
    anggota: Anggota,
    onEditClick: (String) -> Unit = {},
    onDeleteClick: (Anggota) -> Unit = {},
) {
    var deleteConfifrmationRequired by rememberSaveable { mutableStateOf(false) }
    Column (
        modifier = modifier.padding(16.dp)
    ){
        ItemDetailAnggota(
            anggota = anggota,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                onEditClick(anggota.idAnggota.toString())
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
            Text(text = "Update")
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
                contentColor = Color.White
            )
        ) {
            Text(text = "Delete")
        }
    }
    if (deleteConfifrmationRequired){
        DeleteAnggotaConfirmationDialog(
            onDeleteConfirm = {
                deleteConfifrmationRequired = false
                onDeleteClick(anggota)
            },
            onDeleteCancel = {deleteConfifrmationRequired = false},
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ItemDetailAnggota(
    modifier: Modifier = Modifier,
    anggota: Anggota,
){
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {

            ComponentDetailAnggota(judul = "Id Anggota", isinya = anggota.idAnggota.toString())
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailAnggota(judul = "Nama", isinya = anggota.nama)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailAnggota(judul = "Email", isinya = anggota.email)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailAnggota(judul = "No HP", isinya = anggota.nomorTelepon)
            Spacer(modifier = Modifier.padding(4.dp))

        }

    }
}



@Composable
fun ComponentDetailAnggota(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column (
        modifier = modifier.fillMaxWidth(),

        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul: ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
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