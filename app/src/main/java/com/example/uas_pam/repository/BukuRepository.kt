package com.example.uas_pam.repository

import com.example.uas_pam.model.Buku
import com.example.uas_pam.service_api.BukuService
import java.io.IOException

interface BukuRepository{
    suspend fun getBuku(): List<Buku>
    suspend fun getBukuById(idBuku: String): Buku
    suspend fun insertBuku(buku: Buku)
    suspend fun editBuku(idBuku: String, buku: Buku)
    suspend fun deleteBuku(idBuku: String)
}

class NetworkBukuRepository(
    private val bukuApiService: BukuService
): BukuRepository {
    override suspend fun getBuku(): List<Buku> = bukuApiService.getBuku()

    override suspend fun getBukuById(idBuku: String): Buku {
        return bukuApiService.getBukuById(idBuku)
    }

    override suspend fun insertBuku(buku: Buku) {
        bukuApiService.insertBuku(buku)
    }

    override suspend fun editBuku(idBuku: String, buku: Buku) {
        bukuApiService.editBuku(idBuku, buku)
    }

    override suspend fun deleteBuku(idBuku: String) {
        try {
            val response = bukuApiService.deleteBuku(idBuku)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete buku. HTTP Status Code:" +
                            "${response.code()} "
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
