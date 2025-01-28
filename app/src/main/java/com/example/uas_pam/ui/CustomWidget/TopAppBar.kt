package com.example.uas_pam.ui.CustomWidget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    onRefresh: () -> Unit = {}
){
    CenterAlignedTopAppBar(
        title ={ Text(title) },
        actions = {
            Icon(imageVector = Icons.Default.Refresh,contentDescription = null,modifier = Modifier.clickable {
                onRefresh()
            })
        },
        modifier = modifier.fillMaxWidth(),
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = navigateUp
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White // Ikon navigasi juga putih
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor =Color(0xFF2196F3), // Warna background biru
            navigationIconContentColor = Color.White, // Warna ikon navigasi
            titleContentColor = Color.White, // Warna teks judul
            actionIconContentColor = Color.White // Warna ikon aksi
        )
    )
}