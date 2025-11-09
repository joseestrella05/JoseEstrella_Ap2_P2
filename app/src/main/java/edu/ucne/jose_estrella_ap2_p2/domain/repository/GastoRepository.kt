package edu.ucne.jose_estrella_ap2_p2.domain.repository

import edu.ucne.jose_estrella_ap2_p2.domain.model.Gasto

interface GastoRepository{
    suspend fun getGasto(): List<Gasto>
    suspend fun  postGasto(gasto: Gasto)
    suspend fun getGastoById(id: Int): Gasto
    suspend fun updateGasto(id: Int, gasto: Gasto)
}