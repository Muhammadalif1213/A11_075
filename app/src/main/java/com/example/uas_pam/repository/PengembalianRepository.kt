package com.example.uas_pam.repository

import com.example.uas_pam.model.Pengembalian
import com.example.uas_pam.service_api.PengembalianService

interface PengembalianRepository{
    suspend fun getPengembalian(): List<Pengembalian>
    suspend fun insertPengembalian(peminjaman: Pengembalian)
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

    override suspend fun insertPengembalian(peminjaman: Pengembalian) {
        TODO("Not yet implemented")
    }

    override suspend fun getPengembalianById(idPengembalian: Int): Pengembalian {
        TODO("Not yet implemented")
    }

    override suspend fun editPengembalian(idPengembalian: Int, peminjaman: Pengembalian) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePengembalian(idPengembalian: Int) {
        TODO("Not yet implemented")
    }

}