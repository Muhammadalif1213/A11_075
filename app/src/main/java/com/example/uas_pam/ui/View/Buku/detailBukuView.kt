package com.example.uas_pam.ui.View.Buku

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
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailBuku.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
            )
        }
    ){
            innerPadding ->
        StatusDetail(
            detailUiState = detailViewModel.detailBukuUiState,
            retryAction = {detailViewModel.getBukuById()},
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            onEditClick = onEditClick
        )
    }
}

@Composable
fun StatusDetail(
    detailUiState: DetailBukuUiState,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    retryAction: () -> Unit,
){
    when(detailUiState) {
        is DetailBukuUiState.Success ->{
            DetailBukuLayout(
                buku = detailUiState.buku,
                onEditClick = {onEditClick(it)},
                modifier = modifier
            )
        }
        is DetailBukuUiState.Loading -> OnLoading(modifier = Modifier)
        is DetailBukuUiState.Error -> onErr(retryAction ,modifier = Modifier)
    }
}

@Composable
fun DetailBukuLayout(
    modifier: Modifier = Modifier,
    buku: Buku,
    onEditClick: (String) -> Unit = {}
) {
    Column (){
        ItemDetailBuku(
            buku = buku,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                onEditClick(buku.idBuku.toString())
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
fun ItemDetailBuku(
    modifier: Modifier = Modifier,
    buku: Buku
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
            ComponentDetailBuku(judul = "Id Buku", isinya = buku.idBuku.toString())
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBuku(judul = "Judul Buku", isinya = buku.judul)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBuku(judul = "Penulis", isinya = buku.penulis)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBuku(judul = "Kategori", isinya = buku.kategori)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBuku(judul = "Status", isinya = buku.status)
            Spacer(modifier = Modifier.padding(4.dp))

        }
    }
}



@Composable
fun ComponentDetailBuku(
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