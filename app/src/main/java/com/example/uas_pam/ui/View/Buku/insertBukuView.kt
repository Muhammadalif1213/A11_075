package com.example.uas_pam.ui.View.Buku

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.R
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiEntryBuku
import com.example.uas_pam.ui.viewModel.Buku.FormErrorState
import com.example.uas_pam.ui.viewModel.Buku.InsertBukuUiEvent
import com.example.uas_pam.ui.viewModel.Buku.InsertBukuUiState
import com.example.uas_pam.ui.viewModel.Buku.InsertBukuViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBukuScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: InsertBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiEntryBuku.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){
            innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .background(Color(0xFF2196F3))
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(
                painter = painterResource(id = R.drawable.buku),
                contentDescription = "Icon Menu",
                modifier = Modifier.size(150.dp)
            )
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
                    Text(text = "Silahkan Input Data Buku",
                        style = MaterialTheme.typography.titleLarge)
                }
            }
            Card(
                modifier = modifier
                    .background(Color(0xFF2196F3))
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ){
                EntryBody(
                    insertUiState = viewModel.uiState,
                    onBukuValueChange = viewModel::updateInsertBukuState,
                    onSaveClick = {
                        coroutineScope.launch {
                            viewModel.insertBuku()
                            onBackClick()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertBukuUiState,
    onBukuValueChange: (InsertBukuUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(10.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertBukuUiEvent,
            formErrorState = insertUiState.isEntryValid,
            onValueChange = onBukuValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiEvent: InsertBukuUiEvent,
    formErrorState: FormErrorState,
    modifier: Modifier = Modifier,
    onValueChange: (InsertBukuUiEvent) -> Unit = {},
    enabled: Boolean = true,
) {
    val kategoriOptions = listOf("Novel", "Komik", "Fiksi", "Non-Fiksi")
    val statusOptions = listOf("Dipinjam", "Tersedia")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.judul,
            onValueChange = { onValueChange(insertUiEvent.copy(judul = it)) },
            label = { Text(text = "Judul") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorState.judul != null
        )
        if (formErrorState.judul != null) {
            Text(
                text = formErrorState.judul,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = insertUiEvent.penulis,
            onValueChange = { onValueChange(insertUiEvent.copy(penulis = it)) },
            label = { Text(text = "Penulis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorState.penulis != null
        )
        if (formErrorState.penulis != null) {
            Text(
                text = formErrorState.penulis,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        DropDownTextField(
            selectedValue = insertUiEvent.kategori,
            options = kategoriOptions,
            label = "Kategori",
            onValueChangedEvent = { selectedKategori ->
                onValueChange(insertUiEvent.copy(kategori = selectedKategori))
            },
            modifier = Modifier.fillMaxWidth()
        )
        if (formErrorState.kategori != null) {
            Text(
                text = formErrorState.kategori,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        DropDownTextField(
            selectedValue = insertUiEvent.status,
            options = statusOptions,
            label = "Status",
            onValueChangedEvent = { selectedStatus ->
                onValueChange(insertUiEvent.copy(status = selectedStatus))
            },
            modifier = Modifier.fillMaxWidth()
        )
        if (formErrorState.status != null) {
            Text(
                text = formErrorState.status,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownTextField(
    selectedValue: String,
    options: List<String>,
    label: String,
    onValueChangedEvent: (String) -> Unit,
    modifier: Modifier = Modifier
){
    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded},
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = label ) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded,
            onDismissRequest = {expanded = false}) {
            options.forEach{ option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    }
                )
            }
        }
    }
}