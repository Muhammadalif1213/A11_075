package com.example.uas_pam.repository

import com.example.uas_pam.model.Pengembalian
import com.example.uas_pam.service_api.PengembalianService
import java.io.IOException

interface PengembalianRepository{
    suspend fun getPengembalian(): List<Pengembalian>
    suspend fun insertPengembalian(pengembalian: Pengembalian)
    suspend fun getPengembalianById(idPengembalian: Int): Pengembalian
    suspend fun editPengembalian(idPengembalian: Int, peminjaman: Pengembalian)
    suspend fun deletePengembalian(idPengembalian: Int)
}

class NetworkPengembalianRepository(
    private val pengembalianApiService: PengembalianService
): PengembalianRepository {
    override suspend fun getPengembalian(): List<Pengembalian> {
        return pengembalianApiService.getPengembalian()
    }

    override suspend fun insertPengembalian(pengembalian: Pengembalian) {
        pengembalianApiService.insertPengembalian(pengembalian)
    }

    override suspend fun getPengembalianById(idPengembalian: Int): Pengembalian {
        return  pengembalianApiService.getPengembalianById(idPengembalian)
    }

    override suspend fun editPengembalian(idPengembalian: Int, pengembalian: Pengembalian) {
        pengembalianApiService.editPengembalian(idPengembalian,pengembalian)
    }

    override suspend fun deletePengembalian(idPengembalian: Int) {
        try {
            val response = pengembalianApiService.deletePengembalian(idPengembalian)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete pengembalian with ID: " +
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