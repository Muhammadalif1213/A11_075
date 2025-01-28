package com.example.uas_pam.ui.View.Peminjaman

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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar
import com.example.uas_pam.ui.Navigation.DestinasiEntryPeminjaman
import com.example.uas_pam.ui.viewModel.Anggota.ListAnggotaUiState
import com.example.uas_pam.ui.viewModel.Anggota.ListAnggotaViewModel
import com.example.uas_pam.ui.viewModel.Buku.ListBukuUiState
import com.example.uas_pam.ui.viewModel.Buku.ListBukuViewModel
import com.example.uas_pam.ui.viewModel.Peminjaman.AnggotaOption
import com.example.uas_pam.ui.viewModel.Peminjaman.BukuOption
import com.example.uas_pam.ui.viewModel.Peminjaman.InsertPeminjamanUiEvent
import com.example.uas_pam.ui.viewModel.Peminjaman.InsertPeminjamanUiState
import com.example.uas_pam.ui.viewModel.Peminjaman.InsertPeminjamanViewModel
import com.example.uas_pam.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPeminjamanScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModelBuku: ListBukuViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelAnggota: ListAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModel: InsertPeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.loadBukuAndAnggotaOptions()
    }
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiEntryPeminjaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){
            innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ){
            Row (
                modifier = modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ){
                AnggotaTable(
                    listAnggotaUiState = viewModelAnggota.anggotaUiState,
                    modifier = Modifier.padding(16.dp).weight(2f),
                )
                BukuTable(
                    listBukuUiState = viewModelBuku.bukuUiState,
                    modifier = Modifier.padding(16.dp).weight(2f),
                )
            }

            EntryBody(
                insertUiState = viewModel.uiState,
                onPeminjamanValueChange = viewModel::updateInsertPeminjamanState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.insertPeminjaman()
                        onBackClick()
                    }
                },
                anggotaOptions = viewModel.anggotaOptions,
                bukuOptions = viewModel.bukuOptions,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            )
        }
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertPeminjamanUiState,
    onPeminjamanValueChange: (InsertPeminjamanUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    anggotaOptions: List<AnggotaOption>,
    bukuOptions: List<BukuOption>,
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    )  {
        FormInput(
            insertUiEvent = insertUiState.insertPeminjamanUiEvent,
            onValueChange = onPeminjamanValueChange,
            modifier = Modifier.fillMaxWidth(),
            insertUiState = insertUiState,
            anggotaOptions = anggotaOptions,
            bukuOptions = bukuOptions,
            onPeminjamanValueChange = onPeminjamanValueChange
        )
        Button(onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Simpan")
        }
    }
}


@Composable
fun FormInput(
    insertUiEvent: InsertPeminjamanUiEvent,
    modifier: Modifier = Modifier,
    insertUiState: InsertPeminjamanUiState,
    onValueChange: (InsertPeminjamanUiEvent) -> Unit = {},
    onPeminjamanValueChange: (InsertPeminjamanUiEvent) -> Unit,
    enabled: Boolean = true,
    anggotaOptions: List<AnggotaOption>,
    bukuOptions: List<BukuOption>,
) {
    val calendar = java.util.Calendar.getInstance()
    val year = calendar.get(java.util.Calendar.YEAR)
    val month = calendar.get(java.util.Calendar.MONTH)
    val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

    // State untuk DatePickerDialog
    var showDatePickerPeminjaman by remember { mutableStateOf(false) }
    var showDatePickerPengembalian by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Silahkan isi Data Peminjaman",
            style = MaterialTheme.typography.titleMedium)

        // Dropdown untuk memilih Judul Buku
        DropDownTextField(
            selectedValue = insertUiState.insertPeminjamanUiEvent.judulBuku,
            options = bukuOptions.map { it.judul }, // Hanya menampilkan judul
            label = "Judul Buku",
            onValueChangedEvent = { selectedTitle ->
                // Cari ID yang sesuai dengan judul yang dipilih
                val selectedBuku = bukuOptions.find { it.judul == selectedTitle }
                val updatedEvent =
                    insertUiState.insertPeminjamanUiEvent.copy(
                    judulBuku = selectedTitle,
                    idBuku = selectedBuku?.id?.toString() ?: "" // Atur ID sesuai kebutuhan
                )
                onPeminjamanValueChange(updatedEvent)
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Dropdown untuk memilih Nama Anggota
        DropDownTextField(
            selectedValue = insertUiState.insertPeminjamanUiEvent.namaAnggota,
            options = anggotaOptions.map { it.nama },
            label = "Nama Anggota",
            onValueChangedEvent = { selectedName ->
                if (insertUiState.insertPeminjamanUiEvent.idAnggota.isEmpty()) { // Hanya jika belum ada ID
                    val selectedAnggota = anggotaOptions.find { it.nama == selectedName }
                    val updatedEventName =
                        insertUiState.insertPeminjamanUiEvent.copy(
                            namaAnggota = selectedName,
                            idAnggota = selectedAnggota?.id?.toString() ?: "" // Atur sesuai ID sebenarnya dari data anggota
                        )
                    onPeminjamanValueChange(updatedEventName)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = insertUiState.insertPeminjamanUiEvent.idAnggota.isEmpty() // Hanya aktif jika ID belum terisi
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // Input untuk Tanggal Peminjaman
            OutlinedTextField(
                value = insertUiEvent.tanggalPeminjaman,
                onValueChange = {},
                label = { Text(text = "Tanggal Peminjaman") },
                modifier = Modifier
                    .weight(4f)
                    .clickable { showDatePickerPeminjaman = true }, // Buka DatePickerDialog
                enabled = false, // Nonaktifkan input manual
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Green, // Warna border saat fokus
                    unfocusedBorderColor = Color.Green, // Warna border saat tidak fokus
                    disabledBorderColor = Color.Green, // Warna border saat disabled
                    disabledTextColor = Color.Green, // Warna teks saat disabled
                    focusedLabelColor = Color.Green, // Warna label saat fokus
                    unfocusedLabelColor = Color.Green // Warna label saat tidak fokus
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            // Input untuk Tanggal Pengembalian
            OutlinedTextField(
                value = insertUiEvent.tanggalPengembalian,
                onValueChange = {},
                label = { Text(text = "Tanggal Pengembalian") },
                modifier = Modifier
                    .weight(4f)
                    .clickable { showDatePickerPengembalian = true }, // Buka DatePickerDialog
                enabled = false, // Nonaktifkan input manual
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Red, // Warna border saat fokus
                    unfocusedBorderColor = Color.Red, // Warna border saat tidak fokus
                    disabledBorderColor = Color.Red, // Warna border saat disabled
                    disabledTextColor = Color.Red, // Warna teks saat disabled
                    focusedLabelColor = Color.Red, // Warna label saat fokus
                    unfocusedLabelColor = Color.Red // Warna label saat tidak fokus
                )
            )
        }

        // DatePickerDialog untuk Tanggal Peminjaman
        if (showDatePickerPeminjaman) {
            DatePickerDialog(
                onDismissRequest = { showDatePickerPeminjaman = false },
                onDateSelected = { selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "${selectedYear}-${selectedMonth + 1}-${selectedDay}"
                    onValueChange(insertUiEvent.copy(tanggalPeminjaman = formattedDate))
                    showDatePickerPeminjaman = false
                },
                year = year,
                month = month,
                day = day
            )
        }

        // DatePickerDialog untuk Tanggal Pengembalian
        if (showDatePickerPengembalian) {
            DatePickerDialog(
                onDismissRequest = { showDatePickerPengembalian = false },
                onDateSelected = { selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "${selectedYear}-${selectedMonth + 1}-${selectedDay}"
                    onValueChange(insertUiEvent.copy(tanggalPengembalian = formattedDate))
                    showDatePickerPengembalian = false
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
    modifier: Modifier = Modifier,
    enabled: Boolean = true // Tambahkan parameter `enabled`
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded && enabled, // Hanya bisa diperluas jika enabled
        onExpandedChange = { if (enabled) expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true, // Tetap readonly
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                if (enabled) {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            enabled = enabled // Pastikan TextField mengikuti state enabled
        )

        if (enabled) {
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
fun AnggotaTable(
    listAnggotaUiState: ListAnggotaUiState,
    modifier: Modifier = Modifier,
) {
    when (listAnggotaUiState) {
        is ListAnggotaUiState.Loading -> {
            Text(
                text = "Memuat data...",
                modifier = modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        is ListAnggotaUiState.Success -> {
            if (listAnggotaUiState.listAnggota.isEmpty()) {
                Text(
                    text = "Tidak ada data anggota",
                    modifier = modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                // Scrollable column
                Column(
                    modifier = modifier
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState()) // Enable vertical scroll
                ) {
                    Text(text = "Daftar Anggota",
                        style = MaterialTheme.typography.titleMedium)
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
                        HeaderCell(text = "Nama", modifier = Modifier.weight(2f))
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    // Table Rows
                    listAnggotaUiState.listAnggota.forEach { anggota ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            BodyCell(text = anggota.idAnggota.toString(), modifier = Modifier.weight(2f))
                            Spacer(modifier = Modifier.weight(1f))
                            BodyCell(text = anggota.nama, modifier = Modifier.weight(3f))
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
        is ListAnggotaUiState.Error -> {
            Text(
                text = "Gagal memuat data. Coba lagi.",
                modifier = modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun BukuTable(
    listBukuUiState: ListBukuUiState,
    modifier: Modifier = Modifier,
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
            if (listBukuUiState.listBuku.isEmpty()) {
                Text(
                    text = "Tidak ada data buku",
                    modifier = modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()) // Enable vertical scroll
                        .padding(8.dp)
                ) {
                    Text(text = "Daftar Buku",
                        style = MaterialTheme.typography.titleMedium)
                    // Table Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(vertical = 8.dp)
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        HeaderCell(
                            text = "Judul",
                            modifier = Modifier.weight(2f)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        HeaderCell(
                            text = "Status",
                            modifier = Modifier.weight(2f)
                        )
                    }

                    // Table Rows
                    listBukuUiState.listBuku.forEach {buku ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            BodyCell(
                                text = buku.judul,
                                modifier = Modifier.weight(2f)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            BodyCell(
                                text = buku.status,
                                modifier = Modifier
                                    .weight(2f)
                                    .background(
                                        if (buku.status == "Tersedia") Color.Green.copy(alpha = 0.2f)
                                        else Color.Red.copy(alpha = 0.2f)
                                    )
                            )
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
