package com.example.uas_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Peminjaman (
    @SerialName("id_peminjaman")
    val idPeminjaman: Int,

    @SerialName("nama_anggota")
    val namaAnggota: String,

    @SerialName("id_buku")
    val idBuku: Int,

    @SerialName("id_anggota")
    val idAnggota: Int,

    @SerialName("judul_buku")
    val judulBuku: String,

    @SerialName("tanggal_peminjaman")
    val tanggalPeminjaman: String,

    @SerialName("tanggal_pengembalian")
    val tanggalPengembalian: String,

    val status: String
)