package com.example.uas_pam.ui.Navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiHome: DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home"
}

object DestinasiEntryBuku: DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes= "Entry Buku"
}

object DestinasiListBuku: DestinasiNavigasi {
    override val route = "item_list"
    override val titleRes = "Menu Buku"
}

object DestinasiDetailBuku: DestinasiNavigasi {
    override val route = "item_Detail"
    override val titleRes = "Detail Buku"
    const val IDBUKU = "idBuku"
    val routeWithArgs = "$route/{$IDBUKU}"
}

object DestinasiUpdateBuku: DestinasiNavigasi {
    override val route = "item_update"
    override val titleRes= "Update Buku"
    const val IDBUKU = "idBuku"
    val routeWithArgs = "$route/{$IDBUKU}"
}


object DestinasiEntryAnggota: DestinasiNavigasi {
    override val route = "Anggota_entry"
    override val titleRes= "Entry Anggota"
}

object DestinasiListAnggota: DestinasiNavigasi {
    override val route = "anggota_list"
    override val titleRes= "List Anggota"
}

object DestinasiDetailAnggota: DestinasiNavigasi {
    override val route = "anggota_Detail"
    override val titleRes = "Detail Anggota"
    const val IDANGGOTA = "idAnggota"
    val routeWithArgs = "$route/{$IDANGGOTA}"
}

object DestinasiUpdateAnggota: DestinasiNavigasi {
    override val route = "anggota_update"
    override val titleRes= "Update Anggota"
    const val IDANGGOTA = "idAnggota"
    val routeWithArgs = "$route/{$IDANGGOTA}"
}

object DestinasiListPeminjaman: DestinasiNavigasi {
    override val route = "peminjaman_list"
    override val titleRes= "Menu Peminjaman"
}

object DestinasiEntryPeminjaman: DestinasiNavigasi {
    override val route = "peminjaman_entry"
    override val titleRes= "Entry Peminjaman"
}

object DestinasiDetailPeminjaman: DestinasiNavigasi {
    override val route = "peminjaman_Detail"
    override val titleRes = "Detail Peminjaman"
    const val IDPINJAM = "idPeminjaman"
    val routeWithArgs = "$route/{$IDPINJAM}"
}

object DestinasiUpdatePeminjaman: DestinasiNavigasi {
    override val route = "peminjaman_update"
    override val titleRes= "Update Peminjaman"
    const val IDPINJAM = "idPeminjaman"
    val routeWithArgs = "$route/{$IDPINJAM}"
}

object DestinasiListPengembalian: DestinasiNavigasi {
    override val route = "pengembalian_list"
    override val titleRes= "Menu Pengembalian"
}

object DestinasiInsertPengembalian: DestinasiNavigasi {
    override val route = "pengembalian_entry"
    override val titleRes= "Entry Pengembalian"
}

object DestinasiDetailPengembalian: DestinasiNavigasi {
    override val route = "pengembalian_Detail"
    override val titleRes = "Detail Pengembalian"
    const val IDKEMBALI = "idPengembalian"
    val routeWithArgs = "$route/{$IDKEMBALI}"
}

object DestinasiUpdatePengembalian: DestinasiNavigasi {
    override val route = "pengembalian_update"
    override val titleRes= "Update Pengembalian"
    const val IDKEMBALI = "idPengembalian"
    val routeWithArgs = "$route/{$IDKEMBALI}"
}