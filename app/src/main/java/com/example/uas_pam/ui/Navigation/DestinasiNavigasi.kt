package com.example.uas_pam.ui.Navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiEntryBuku: DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes= "Entry Buku"
}

object DestinasiListBuku: DestinasiNavigasi {
    override val route = "item_list"
    override val titleRes = "List Buku"
}
