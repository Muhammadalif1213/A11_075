package com.example.uas_pam.ui.View.Anggota

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: ListAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiListAnggota.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getAnggota()
                }

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAnggotaEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Mahasiswa"
                )
            }
        }
    ){
            innerPadding ->
        HomeStatus(
            listAnggotaUiState = viewModel.anggotaUiState,
            retryAction = { viewModel.getAnggota() },
            modifier = Modifier
                .padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteAnggota(it.idAnggota)
            }
        )
    }
}


@Composable
fun HomeStatus(
    listAnggotaUiState: ListAnggotaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Anggota) -> Unit = {},
    onDetailClick: (String) -> Unit = {}
){
    when(listAnggotaUiState) {
        is ListAnggotaUiState.Loading -> OnLoading(modifier = Modifier.fillMaxSize())

        is ListAnggotaUiState.Success ->
            if (listAnggotaUiState.listAnggota.isEmpty()) {
                return Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data anggota")
                }
            }else{
                MhsLayout(
                    anggota = listAnggotaUiState.listAnggota,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onDetailClick = {
                        onDetailClick(it.idAnggota.toString())
                    }
                )
            }
        is ListAnggotaUiState.Error -> onErr(retryAction, modifier = Modifier.fillMaxSize())
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
fun MhsLayout(
    anggota: List<Anggota>,
    modifier: Modifier = Modifier,
    onDeleteClick: (Anggota) -> Unit = {},
    onDetailClick: (Anggota) -> Unit
){
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(anggota){anggota ->
            AnggotaCard(
                anggota = anggota,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(anggota) },
                onDeleteClick = {
                    onDeleteClick(anggota)
                }
            )
        }
    }
}


@Composable
fun AnggotaCard(
    anggota: Anggota,
    modifier: Modifier = Modifier,
    onDeleteClick: (Anggota) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = anggota.nama,
                    style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(anggota) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                Text(
                    text = anggota.idAnggota.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier =  Modifier
                )
            }
            Text(
                text = anggota.email,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = anggota.nomorTelepon,
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}