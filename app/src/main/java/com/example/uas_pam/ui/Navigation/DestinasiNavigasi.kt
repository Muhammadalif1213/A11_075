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
    override val titleRes = "List Buku"
}

object DestinasiDetailBuku: DestinasiNavigasi {
    override val route = "item_Detail"
    override val titleRes = "Detail Buku"
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
