package edu.ucne.jose_estrella_ap2_p2.domain.use_case

import edu.ucne.jose_estrella_ap2_p2.domain.repository.GastoRepository

class GetGastosUseCase(private val repository: GastoRepository) {
    suspend operator fun invoke() = repository.getGasto()
}