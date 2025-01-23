package com.example.uas_pam.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uas_pam.ui.View.Anggota.EntryAnggotaScreen
import com.example.uas_pam.ui.View.Anggota.ListAnggotaScreen
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
            onNavigateMenuAnggota ={ navController.navigate(DestinasiListAnggota.route) },
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

        composable(DestinasiEntryAnggota.route) {
            EntryAnggotaScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(DestinasiListAnggota.route){
            ListAnggotaScreen(
                navigateToAnggotaEntry = { navController.navigate(DestinasiEntryAnggota.route) },
                onDetailClick = {
                }
            )
        }
    }
}