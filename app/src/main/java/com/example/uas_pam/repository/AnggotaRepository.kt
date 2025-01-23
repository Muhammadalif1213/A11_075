package com.example.uas_pam.repository

import com.example.uas_pam.model.Anggota
import com.example.uas_pam.service_api.AnggotaService
import java.io.IOException

interface AnggotaRepository{
    suspend fun getAnggota(): List<Anggota>
    suspend fun getAnggotaById(idAnggota: Int): Anggota
    suspend fun insertAnggota(anggota: Anggota)
    suspend fun editAnggota(idAnggota: Int, anggota: Anggota)
    suspend fun deleteAnggota(idAnggota: Int)
}

class NetworkAnggotaRepository(
    private val anggotaApiService: AnggotaService
): AnggotaRepository{
    override suspend fun getAnggota(): List<Anggota> = anggotaApiService.getAnggota()

    override suspend fun getAnggotaById(idAnggota: Int): Anggota {
        TODO("Not yet implemented")
    }

    override suspend fun insertAnggota(anggota: Anggota) {
        anggotaApiService.insertAnggota(anggota)
    }

    override suspend fun editAnggota(idAnggota: Int, anggota: Anggota) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAnggota(idAnggota: Int) {
        try {
            val response = anggotaApiService.deleteAnggota(idAnggota)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete anggota. HTTP Status Code:" +
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