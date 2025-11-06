package edu.ucne.jose_estrella_ap2_p2.presentation.Gasto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.jose_estrella_ap2_p2.domain.model.Gasto
import edu.ucne.jose_estrella_ap2_p2.domain.use_case.GetGastosUseCase
import edu.ucne.jose_estrella_ap2_p2.domain.use_case.PostGastoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GastoViewModel(
    private val getGastosUseCase: GetGastosUseCase,
    private val postGastoUseCase: PostGastoUseCase
) : ViewModel() {

    private val _gastos = MutableStateFlow<List<Gasto>>(emptyList())
    val gastos: StateFlow<List<Gasto>> = _gastos

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    fun cargarGastos() {
        viewModelScope.launch {
            try {
                _gastos.value = getGastosUseCase()
            } catch (e: Exception) {
                _mensaje.value = "Error: ${e.message}"
            }
        }
    }

    fun enviarGasto(gasto: Gasto) {
        viewModelScope.launch {
            try {
                postGastoUseCase(gasto)
                cargarGastos()
            } catch (e: Exception) {
                _mensaje.value = "Error al enviar: ${e.message}"
            }
        }
    }
}