package edu.ucne.jose_estrella_ap2_p2.data.remote.api

import edu.ucne.jose_estrella_ap2_p2.data.remote.dto.GastoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GastoApiServices{
    @GET("api/Gastos")
    suspend fun getGasto() : List<GastoDto>
    @POST("api/Gastos")
    suspend fun postGasto(@Body gasto: GastoDto)
}