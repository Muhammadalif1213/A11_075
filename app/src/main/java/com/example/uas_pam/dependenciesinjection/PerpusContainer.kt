package com.example.uas_pam.dependenciesinjection

import com.example.uas_pam.repository.AnggotaRepository
import com.example.uas_pam.repository.BukuRepository
import com.example.uas_pam.repository.NetworkAnggotaRepository
import com.example.uas_pam.repository.NetworkBukuRepository
import com.example.uas_pam.repository.NetworkPeminjamanRepository
import com.example.uas_pam.repository.NetworkPengembalianRepository
import com.example.uas_pam.repository.PeminjamanRepository
import com.example.uas_pam.repository.PengembalianRepository
import com.example.uas_pam.service_api.AnggotaService
import com.example.uas_pam.service_api.BukuService
import com.example.uas_pam.service_api.PeminjamanService
import com.example.uas_pam.service_api.PengembalianService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val bukuRepository: BukuRepository
    val anggotaRepository: AnggotaRepository
    val peminjamanRepository: PeminjamanRepository
    val pengembalianRepository: PengembalianRepository
}

class PerpusContainer: AppContainer{
    private val baseUrl = "http://10.0.2.2:80/perpusUCP/"
    private val json = Json{ignoreUnknownKeys = true}
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory ("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val bukuService: BukuService by lazy {
        retrofit.create(BukuService::class.java)}

    override val bukuRepository: BukuRepository by lazy {
        NetworkBukuRepository(bukuService)
    }

    private val anggotaService: AnggotaService by lazy {
        retrofit.create(AnggotaService::class.java)}

    override val anggotaRepository: AnggotaRepository by lazy {
        NetworkAnggotaRepository(anggotaService)
    }

    private val peminjamanService: PeminjamanService by lazy {
        retrofit.create(PeminjamanService::class.java)
    }
    override val peminjamanRepository: PeminjamanRepository by lazy {
        NetworkPeminjamanRepository(peminjamanService)
    }

    private val pengembalianService: PengembalianService by lazy {
        retrofit.create(PengembalianService::class.java)
    }

    override val pengembalianRepository: PengembalianRepository by lazy {
        NetworkPengembalianRepository(pengembalianService)
    }
}