package edu.ucne.jose_estrella_ap2_p2.data.remote.api

import edu.ucne.jose_estrella_ap2_p2.data.remote.dto.GastoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GastoApiServices{
    @GET("api/Gastos")
    suspend fun getGasto() : List<GastoDto>
    @POST("api/Gastos")
    suspend fun postGasto(@Body gasto: GastoDto)
    @GET("api/Gastos/{id}")
    suspend fun getGastoById(@Path("id") id: Int): GastoDto
    @PUT("api/Gastos/{id}")
    suspend fun putGasto(@Path("id") id: Int, @Body gasto: GastoDto)
}