package com.example.uas_pam.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uas_pam.ui.View.Anggota.DetailAnggotaView
import com.example.uas_pam.ui.View.Anggota.EntryAnggotaScreen
import com.example.uas_pam.ui.View.Anggota.ListAnggotaScreen
import com.example.uas_pam.ui.View.Anggota.updateAnggotaView
import com.example.uas_pam.ui.View.Buku.DetailView
import com.example.uas_pam.ui.View.Buku.EntryBukuScreen
import com.example.uas_pam.ui.View.Buku.HomeScreen
import com.example.uas_pam.ui.View.Buku.updateBukuView
import com.example.uas_pam.ui.View.HomeAppView
import com.example.uas_pam.ui.View.Peminjaman.EntryPeminjamanScreen
import com.example.uas_pam.ui.View.Peminjaman.PeminjamanScreen

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
            onNavigateAddBrg = {navController.navigate(DestinasiListPeminjaman.route)},
            onNavigateListBrg = {}
        )
        }
        composable(DestinasiEntryBuku.route) {
            EntryBukuScreen(
                navigateBack = { navController.popBackStack() },
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(DestinasiListBuku.route) {
            HomeScreen(
                NavigateBack = { navController.navigateUp() },
                navigateToItemEntry = { navController.navigate(DestinasiEntryBuku.route) },
                onDetailClick = { idBuku ->
                    val idBuku = idBuku
                    navController.navigate("${"${DestinasiDetailBuku.route}/$idBuku"}")
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
                onDetailClick = { idAnggota ->
                    val idAnggota = idAnggota
                    navController.navigate("${"${DestinasiDetailAnggota.route}/$idAnggota"}")
                },
                NavigateUp = { navController.navigateUp() }
            )
        }
        composable(DestinasiDetailAnggota.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailAnggota.IDANGGOTA){
                    type = NavType.IntType
                }
            )
        ) {
            val idAnggota = it.arguments?.getInt(DestinasiDetailAnggota.IDANGGOTA)
            idAnggota?.let { idAnggota ->
                DetailAnggotaView(
                    navigateBack = { navController.navigateUp() },
                    onEditClick = { navController.navigate("${DestinasiUpdateAnggota.route}/$idAnggota") },
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        composable(DestinasiDetailBuku.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailBuku.IDBUKU){
                    type = NavType.IntType
                }
            )
        ) {
            val idBuku = it.arguments?.getInt(DestinasiDetailBuku.IDBUKU)
            idBuku?.let { idBuku ->
                DetailView(
                    navigateBack = { navController.navigateUp() },
                    onEditClick = {navController.navigate("${DestinasiUpdateBuku.route}/$idBuku")},
                    )
            }
        }
        composable(
            DestinasiUpdateBuku.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateBuku.IDBUKU) {
                    type = NavType.IntType
                }
            )
        ){
            val idBuku = it.arguments?.getInt(DestinasiUpdateBuku.IDBUKU)
            idBuku.let { idBuku ->
                updateBukuView(
                    navigateBack = { navController.navigateUp() }
                )
            }
        }

        composable(
            DestinasiUpdateAnggota.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateAnggota.IDANGGOTA) {
                    type = NavType.IntType
                }
            )
        ){
            val idAnggota = it.arguments?.getInt(DestinasiUpdateAnggota.IDANGGOTA)
            idAnggota.let { idAnggota ->
                updateAnggotaView(
                    navigateBack = { navController.navigateUp() }
                )
            }
        }
        composable(DestinasiListPeminjaman.route) {
            PeminjamanScreen(
                NavigateBack = { navController.navigateUp() },
                navigateToItemEntry = { navController.navigate(DestinasiEntryPeminjaman.route) },
            )
        }
        composable(DestinasiEntryPeminjaman.route) {
            EntryPeminjamanScreen(
                navigateBack = { navController.navigateUp() },
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}