package com.example.uas_pam.ui.View.Anggota

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.model.Anggota
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiDetailAnggota
import com.example.uas_pam.ui.viewModel.Anggota.DetailAnggotaUiState
import com.example.uas_pam.ui.viewModel.Anggota.DetailAnggotaViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAnggotaView(
    modifier: Modifier = Modifier,
    detailViewModel: DetailAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit,
    navigateBack: () -> Unit,
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
            onEditClick = onEditClick
        )
    }
}

@Composable
fun StatusDetail(
    detailUiState: DetailAnggotaUiState,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    retryAction: () -> Unit,
){
    when(detailUiState) {
        is DetailAnggotaUiState.Success ->{
            DetailAnggotaLayout(
                anggota = detailUiState.anggota,
                onEditClick = {onEditClick(it)},
                modifier = modifier
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
    onEditClick: (String) -> Unit = {}
) {
    Column (){
        ItemDetailAnggota(
            anggota = anggota,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                onEditClick(anggota.idAnggota.toString())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            Text(text = "Update")
        }
    }
}


@Composable
fun ItemDetailAnggota(
    modifier: Modifier = Modifier,
    anggota: Anggota
){
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
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