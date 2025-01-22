package com.example.uas_pam.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uas_pam.ui.View.Buku.EntryBukuScreen
import com.example.uas_pam.ui.View.Buku.HomeScreen
import com.example.uas_pam.ui.View.HomeAppView

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
        composable(
            route = DestinasiHome.route
        ) { HomeAppView(
            modifier = modifier,
            onNavigateAddSup ={ navController.navigate(DestinasiEntryBuku.route) },
            onNavigateMenuBuku = {navController.navigate(DestinasiListBuku.route)},
            onNavigateAddBrg = {},
            onNavigateListBrg = {}
        )
        }
        composable(DestinasiEntryBuku.route) {
            EntryBukuScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(DestinasiListBuku.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryBuku.route) },
                onDetailClick = {
                }
            )
        }
    }
}