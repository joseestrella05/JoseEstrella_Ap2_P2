package edu.ucne.jose_estrella_ap2_p2.data.remote.dto

import edu.ucne.jose_estrella_ap2_p2.domain.model.Gasto

class GastoDto (
    val gastoId: Int,
    val fecha: String,
    val suplidor : String,
    val ncf: String,
    val itbis: Double,
    val monto: Double
)
{
    fun toDomain() = Gasto(gastoId,fecha,suplidor,ncf,itbis,monto)
}
fun Gasto.toDto() = GastoDto(gastoId, fecha, suplidor, ncf, itbis, monto)


