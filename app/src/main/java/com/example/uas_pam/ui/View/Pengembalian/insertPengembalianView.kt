package com.example.uas_pam.ui.View.Pengembalian

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiInsertPengembalian
import com.example.uas_pam.ui.viewModel.Peminjaman.ListPeminjamanUiState
import com.example.uas_pam.ui.viewModel.Peminjaman.ListPeminjamanViewModel
import com.example.uas_pam.ui.viewModel.Pengembalian.BukuOptionPengembalian
import com.example.uas_pam.ui.viewModel.Pengembalian.InsertPengembalianUiEvent
import com.example.uas_pam.ui.viewModel.Pengembalian.InsertPengembalianUiState
import com.example.uas_pam.ui.viewModel.Pengembalian.InsertPengembalianViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPengembalianScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: InsertPengembalianViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelPeminjaman: ListPeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.loadBukuOptions()
    }
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiInsertPengembalian.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){
            innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "List Peminjaman Buku",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 30.dp))

            PeminjamanTablePengembalian(
                listPeminjamanUiState = viewModelPeminjaman.peminjamanUiState,
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = "Silahkan isi data pengembalian",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 30.dp))

            EntryPengembalianBody(
                insertUiState = viewModel.uiState,
                onPengembalianValueChange = viewModel::updateInsertPengembalianState,
                bukuOptionsPengembalian = viewModel.bukuOptions,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                onSavePengembalianClick = {
                    coroutineScope.launch {
                        viewModel.insertPengembalian()
                        onBackClick()
                    }
                }
            )
        }
    }
}


@Composable
fun EntryPengembalianBody(
    insertUiState: InsertPengembalianUiState,
    onPengembalianValueChange: (InsertPengembalianUiEvent) -> Unit,
    bukuOptionsPengembalian: List<BukuOptionPengembalian>,
    modifier: Modifier = Modifier,
    onSavePengembalianClick: () -> Unit = {}

){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    )  {
        FormInput(
            insertUiEvent = insertUiState.insertPengembalianUiEvent,
            onPengembalianValueChange = onPengembalianValueChange,
            modifier = Modifier.fillMaxWidth(),
            insertUiState = insertUiState,
            bukuOptions = bukuOptionsPengembalian,
            enabled = false
        )
        Button(onClick = onSavePengembalianClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormInput(
    insertUiEvent: InsertPengembalianUiEvent,
    modifier: Modifier = Modifier,
    insertUiState: InsertPengembalianUiState,
    onValueChange: (InsertPengembalianUiEvent) -> Unit = {},
    onPengembalianValueChange: (InsertPengembalianUiEvent) -> Unit,
    enabled: Boolean = true,
    bukuOptions: List<BukuOptionPengembalian>,
) {
    Log.d("FormInput", "bukuList: $bukuOptions")

    val calendar = java.util.Calendar.getInstance()
    val year = calendar.get(java.util.Calendar.YEAR)
    val month = calendar.get(java.util.Calendar.MONTH)
    val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

    // State untuk DatePickerDialog
    var showDatePickerPengembalian by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Dropdown untuk memilih Judul Buku
        DropDownTextField(
            selectedValue = insertUiState.insertPengembalianUiEvent.judul,
            options = bukuOptions.map { it.judul }, // Hanya menampilkan judul
            label = "Judul Buku",
            onValueChangedEvent = { selectedTitle ->
                // Cari ID yang sesuai dengan judul yang dipilih
                val selectedBuku = bukuOptions.find { it.judul == selectedTitle }
                val updatedEvent =
                    insertUiState.insertPengembalianUiEvent.copy(
                        judul = selectedTitle,
                        idPeminjaman = selectedBuku?.idPeminjaman?.toString() ?: "" // Atur ID sesuai kebutuhan
                    )
                onPengembalianValueChange(updatedEvent)
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Input untuk Tanggal Pengembalian
        OutlinedTextField(
            value = insertUiEvent.tanggalDikembalikan,
            onValueChange = {},
            label = { Text(text = "Tanggal Dikembalikan") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePickerPengembalian = true }, // Buka DatePickerDialog
            enabled = false, // Nonaktifkan input manual
            singleLine = true
        )

        // DatePickerDialog untuk Tanggal Pengembalian
        if (showDatePickerPengembalian) {
            DatePickerDialog(
                onDismissRequest = { showDatePickerPengembalian = false },
                onDateSelected = { selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "${selectedYear}-${selectedMonth + 1}-${selectedDay}"
                    val updatedEvent = insertUiEvent.copy(tanggalDikembalikan = formattedDate) // Update event
                    onPengembalianValueChange(updatedEvent) // Perbarui state dengan event baru
                    showDatePickerPengembalian = false // Tutup dialog
                },
                year = year,
                month = month,
                day = day
            )

        }

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(20.dp)
        )
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
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option: String ->
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


@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (year: Int, month: Int, day: Int) -> Unit,
    year: Int,
    month: Int,
    day: Int
) {
    val datePickerDialog = android.app.DatePickerDialog(
        LocalContext.current,
        { _, selectedYear, selectedMonth, selectedDay ->
            onDateSelected(selectedYear, selectedMonth, selectedDay)
        },
        year,
        month,
        day
    )
    LaunchedEffect(Unit) {
        datePickerDialog.show()
    }
    DisposableEffect(Unit) {
        onDispose {
            datePickerDialog.dismiss()
        }
    }
}

@Composable
fun PeminjamanTablePengembalian(
    listPeminjamanUiState: ListPeminjamanUiState,
    modifier: Modifier = Modifier,
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
                                .padding(horizontal = 8.dp),
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