package com.example.uas_pam.service_api

import com.example.uas_pam.model.Pengembalian
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PengembalianService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("bacapengembalian.php")
    suspend fun getPengembalian(): List<Pengembalian>

    @POST("insertpengembalian.php")
    suspend fun insertPengembalian(@Body pengembalian: Pengembalian)

    @GET("baca1pengembalian.php")
    suspend fun getPengembalianById(@Query("id_pengembalian") idPengembalian: Int): Pengembalian

    @PUT("editpengembalian.php/{id_pengembalian}")
    suspend fun editPengembalian(@Query("id_pengembalian") idPengembalian: Int, @Body pengembalian: Pengembalian)

    @DELETE("deletepengembalian.php/{id_pengembalian}")
    suspend fun deletePengembalian(@Query("id_pengembalian") idPengembalian: Int): Response<Void>
}