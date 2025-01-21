package com.example.uas_pam.dependenciesinjection

import com.example.uas_pam.repository.BukuRepository
import com.example.uas_pam.repository.NetworkBukuRepository
import com.example.uas_pam.service_api.BukuService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val bukuRepository: BukuRepository
}

class PerpusContainer: AppContainer{
    private val baseUrl = "http://10.0.2.2:3000/api/perpus/"
    private val json = Json{ignoreUnknownKeys = true}
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory ("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val bukuService: BukuService by lazy {
        retrofit.create(BukuService::class.java)}


    override val bukuRepository: BukuRepository by lazy {
        NetworkBukuRepository(bukuService)
    }
}