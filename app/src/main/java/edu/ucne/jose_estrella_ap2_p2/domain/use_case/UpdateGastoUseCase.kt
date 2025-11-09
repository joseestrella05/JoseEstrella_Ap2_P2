package edu.ucne.jose_estrella_ap2_p2.domain.use_case

import edu.ucne.jose_estrella_ap2_p2.domain.model.Gasto
import edu.ucne.jose_estrella_ap2_p2.domain.repository.GastoRepository

class UpdateGastoUseCase(private val repository: GastoRepository) {
    suspend operator fun invoke(id: Int, gasto: Gasto) {
        repository.updateGasto(id, gasto)
    }
}
