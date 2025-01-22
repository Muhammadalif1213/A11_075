package com.example.uas_pam.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uas_pam.ui.View.Buku.EntryBukuScreen

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    )
    {
        composable(DestinasiEntryBuku.route) {
            EntryBukuScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}