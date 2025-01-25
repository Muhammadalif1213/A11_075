package com.example.uas_pam.repository

import com.example.uas_pam.model.Peminjaman
import com.example.uas_pam.service_api.PeminjamanService

interface PeminjamanRepository{
    suspend fun getPeminjaman(): List<Peminjaman>
    suspend fun insertPeminjaman(peminjaman: Peminjaman)
    suspend fun getPeminjamanById(idPeminjaman: Int): Peminjaman
    suspend fun editPeminjaman(idPeminjaman: Int, peminjaman: Peminjaman)
    suspend fun deletePeminjaman(idPeminjaman: Int)

}

class NetworkPeminjamanRepository(
    private val peminjamanApiService: PeminjamanService
): PeminjamanRepository {
    override suspend fun getPeminjaman(): List<Peminjaman> = peminjamanApiService.getPeminjaman()

    override suspend fun insertPeminjaman(peminjaman: Peminjaman) {
        peminjamanApiService.insertPeminjaman(peminjaman)
    }

    override suspend fun getPeminjamanById(idPeminjaman: Int): Peminjaman {
        return peminjamanApiService.getPeminjamanById(idPeminjaman)
    }

    override suspend fun editPeminjaman(idPeminjaman: Int, peminjaman: Peminjaman) {
        peminjamanApiService.editPeminjaman(idPeminjaman, peminjaman)
    }

    override suspend fun deletePeminjaman(idPeminjaman: Int) {
        try {
            val response = peminjamanApiService.deletePeminjaman(idPeminjaman)
            if (!response.isSuccessful) {
                throw Exception(
                    "Failed to delete peminjaman with ID: " +
                            "${response.code()}"
                )
            }else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

}