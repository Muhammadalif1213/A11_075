package com.example.uas_pam.ui.View

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.uas_pam.R
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppView(
    modifier: Modifier = Modifier,
    onNavigateMenuAnggota: () -> Unit,
    onNavigateAddBrg: () -> Unit,
    onNavigateMenuBuku: () -> Unit,
    onNavigateListBrg: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CustomTopAppBar(
                title = "",
                canNavigateBack = false,
                navigateUp = {},
                onRefresh = {},
            )
        }
    ) { innerPadding ->
        BodyHome(
            modifier = Modifier.padding(innerPadding),
            onBrgListClick = { onNavigateListBrg() },
            onMenuAnggotaClick = { onNavigateMenuAnggota() },
            onMenuBukuClick = { onNavigateMenuBuku() },
            onAddBrgClick = { onNavigateAddBrg() }
        )
    }
}

@Composable
fun BodyHome(
    modifier: Modifier = Modifier,
    onBrgListClick: () -> Unit,
    onMenuAnggotaClick: () -> Unit,
    onAddBrgClick: () -> Unit,
    onMenuBukuClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2196F3)) // Latar belakang biru
            .padding(16.dp)
    ) {
        // Menambahkan gambar latar belakang untuk suasana yang lebih hidup
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.listproduk),
                contentDescription = "Background Perpustakaan",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .alpha(0.5f)
            )
            // Teks Selamat Datang
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Selamat Datang di Perpustakaan!",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Temukan berbagai buku dan layanan yang kami tawarkan.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Tombol Menu dengan animasi
        AnimatedVisibility(visible = true) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardMenuLong(
                    namaMenu = "Menu Peminjaman",
                    onClick = onAddBrgClick,
                    namaGambar = R.drawable.peminjamanbuku
                )
            }
        }

        AnimatedVisibility(visible = true) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardMenu(
                    namaMenu = "Menu Buku",
                    onClick = onMenuBukuClick,
                    namaGambar = R.drawable.buku
                )
                CardMenu(
                    namaMenu = "Menu Anggota",
                    onClick = onMenuAnggotaClick,
                    namaGambar = R.drawable.anggotaa
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMenuLong(
    namaMenu: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    namaGambar: Int
) {
    Box(
        modifier = modifier.padding(8.dp).background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
    ) {
        Card(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color(0xFF42A5F5)) // Warna cyan untuk header
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Image(
                        painter = painterResource(id = namaGambar),
                        contentDescription = "Icon Menu",
                        modifier = Modifier.size(110.dp)
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        text = namaMenu,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMenu(
    namaMenu: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    namaGambar: Int
) {
    Box(
        modifier = modifier.padding(8.dp).background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
    ) {
        Card(
            onClick = onClick,
            modifier = modifier
                .width(160.dp)
                .height(200.dp)
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color(0xFF42A5F5)) // Warna cyan untuk header
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Image(
                        painter = painterResource(id = namaGambar),
                        contentDescription = "Icon Menu",
                        modifier = Modifier.size(90.dp)
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        text = namaMenu,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
