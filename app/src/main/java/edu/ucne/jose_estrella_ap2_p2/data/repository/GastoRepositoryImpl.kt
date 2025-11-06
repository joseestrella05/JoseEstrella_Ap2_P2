package edu.ucne.jose_estrella_ap2_p2.data.repository

import edu.ucne.jose_estrella_ap2_p2.data.remote.api.GastoApiServices
import edu.ucne.jose_estrella_ap2_p2.data.remote.dto.toDto
import edu.ucne.jose_estrella_ap2_p2.domain.model.Gasto
import edu.ucne.jose_estrella_ap2_p2.domain.repository.GastoRepository

class GastoRepositoryImpl(private  val api: GastoApiServices) : GastoRepository{
    override suspend fun getGasto(): List<Gasto> {
        return api.getGasto().map { it.toDomain() }
    }

    override suspend fun postGasto(gasto: Gasto) {
        api.postGasto(gasto.toDto())
    }
}
