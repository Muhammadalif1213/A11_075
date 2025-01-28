package com.example.uas_pam.ui.View.Buku

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.R
import com.example.uas_pam.model.Buku
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiListBuku
import com.example.uas_pam.ui.viewModel.Buku.ListBukuUiState
import com.example.uas_pam.ui.viewModel.Buku.ListBukuViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    NavigateBack: () -> Unit,
    viewModel: ListBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiListBuku.titleRes,
                navigateUp = NavigateBack,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getBuku()
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = navigateToItemEntry,
                text = { Text("Tambah Buku") },
                icon = { Icon(Icons.Default.Add, contentDescription = "Add Buku") },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Cari buku...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            singleLine = true,
            shape = MaterialTheme.shapes.large
            )

            // Table for Buku Data
            BukuTable(
                listBukuUiState = viewModel.bukuUiState,
                searchQuery = searchQuery,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onDetailClick = onDetailClick
            )
        }
    }
}

@Composable
fun HomeStatus(
    listBukuUiState: ListBukuUiState,
    searchQuery: String,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {}
) {
    when (listBukuUiState) {
        is ListBukuUiState.Loading -> OnLoading(modifier = Modifier.fillMaxSize())

        is ListBukuUiState.Success -> {
            val filteredBooks = listBukuUiState.listBuku.filter {
                it.judul.contains(searchQuery, ignoreCase = true)
            }
            if (filteredBooks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data buku")
                }
            } else {
                MhsLayout(
                    buku = filteredBooks,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idBuku.toString())
                    }
                )
            }
        }
        is ListBukuUiState.Error -> onErr(retryAction, modifier = Modifier.fillMaxSize())
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
    buku: List<Buku>,
    modifier: Modifier = Modifier,
    onDetailClick: (Buku) -> Unit
){
    var deleteConfifrmationRequired by rememberSaveable { mutableStateOf(false) }
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(buku){buku ->
            BukuCard(
                buku = buku,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(buku) }
            )
        }
    }
}



@Composable
fun BukuCard(
    buku: Buku,
    modifier: Modifier = Modifier
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
                    text = buku.judul,
                    style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.weight(1f))
                Text(
                    text = buku.idBuku.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier =  Modifier
                )
            }
            Text(
                text = buku.penulis,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = buku.status,
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}

@Composable
fun BukuTable(
    listBukuUiState: ListBukuUiState,
    searchQuery: String,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit // Tambahkan parameter untuk navigasi ke detail
) {
    when (listBukuUiState) {
        is ListBukuUiState.Loading -> {
            Text(
                text = "Memuat data...",
                modifier = modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        is ListBukuUiState.Success -> {
            val filteredBooks = listBukuUiState.listBuku.filter {
                it.judul.contains(searchQuery, ignoreCase = true) ||
                        it.penulis.contains(searchQuery, ignoreCase = true) ||
                        it.kategori.contains(searchQuery, ignoreCase = true)
            }
            if (filteredBooks.isEmpty()) {
                Text(
                    text = "Tidak ada data buku",
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
                        HeaderCell(text = "Judul", modifier = Modifier.weight(2f))
                        HeaderCell(text = "Penulis", modifier = Modifier.weight(2f))
                        HeaderCell(text = "Kategori", modifier = Modifier.weight(2f))
                        HeaderCell(text = "Status", modifier = Modifier.weight(2f))
                    }

                    // Table Rows
                    filteredBooks.forEach { buku ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onDetailClick(buku.idBuku.toString()) } // Tambahkan klik untuk navigasi
                                .padding(vertical = 4.dp)
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            BodyCell(text = buku.idBuku.toString(), modifier = Modifier.weight(2f))
                            BodyCell(text = buku.judul, modifier = Modifier.weight(2f))
                            BodyCell(text = buku.penulis, modifier = Modifier.weight(2f))
                            BodyCell(text = buku.kategori, modifier = Modifier.weight(2f))
                            BodyCell(text = buku.status, modifier = Modifier.weight(2f))
                        }
                    }
                }
            }
        }
        is ListBukuUiState.Error -> {
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
