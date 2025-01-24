package com.example.uas_pam.service_api

import com.example.uas_pam.model.Peminjaman
import retrofit2.http.GET
import retrofit2.http.Headers

interface PeminjamanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("bacapeminjaman.php")
    suspend fun getPeminjaman(): List<Peminjaman>
}