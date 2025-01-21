package com.example.uas_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Buku (
    @SerialName("id_buku")
    val idBuku: String,

    val judul: String,
    val penulis: String,
    val kategori: String,
    val status: String
)