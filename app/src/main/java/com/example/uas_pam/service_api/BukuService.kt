package com.example.uas_pam.service_api

import com.example.uas_pam.model.Buku
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface BukuService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("bacabuku.php")
    suspend fun getBuku(): List<Buku>

    @GET("baca1buku.php")
    suspend fun getBukuById(@Query("id_buku") idBuku: String): Buku

    @POST("tambahbuku.php")
    suspend fun insertBuku(@Body buku: Buku)

    @PUT("editbuku.php/{id_buku}")
    suspend fun editBuku(@Query("id_buku") idBuku: String, @Body buku: Buku)

    @DELETE("deletebuku.php/{id_buku}")
    suspend fun deleteBuku(@Query("id_buku") idBuku: String)

}