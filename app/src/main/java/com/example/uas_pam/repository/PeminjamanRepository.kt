package com.example.uas_pam.repository

import com.example.uas_pam.model.Peminjaman
import com.example.uas_pam.service_api.PeminjamanService

interface PeminjamanRepository{
    suspend fun getPeminjaman(): List<Peminjaman>
}

class NetworkPeminjamanRepository(
    private val peminjamanApiService: PeminjamanService
): PeminjamanRepository {
    override suspend fun getPeminjaman(): List<Peminjaman> = peminjamanApiService.getPeminjaman()

}