package com.example.uas_pam.service_api

import com.example.uas_pam.model.Anggota
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AnggotaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("bacaanggota.php")
    suspend fun getAnggota(): List<Anggota>

    @GET("baca1anggota.php")
    suspend fun getAnggotaById(@Query("id_anggota") idAnggota: String): Anggota

    @POST("tambahanggota.php")
    suspend fun insertAnggota(anggota: Anggota)

    @PUT("editanggota.php/{id_anggota}")
    suspend fun editAnggota(@Query("id_anggota") idAnggota: String, @Body anggota: Anggota)

    @DELETE("deleteanggota.php/{id_anggota}")
    suspend fun deleteAnggota(@Query("id_anggota") idAnggota: String)


}