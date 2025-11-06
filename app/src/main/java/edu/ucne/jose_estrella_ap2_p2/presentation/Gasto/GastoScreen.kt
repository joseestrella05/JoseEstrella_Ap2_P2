package edu.ucne.jose_estrella_ap2_p2.presentation.Gasto

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucne.jose_estrella_ap2_p2.domain.model.Gasto
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GastoScreen(viewModel: GastoViewModel) {
    val gastos by viewModel.gastos.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()

    val scope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    var suplidor by remember { mutableStateOf("") }
    var ncf by remember { mutableStateOf("") }
    var itbis by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.cargarGastos()
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text("Nuevo Gasto", style = MaterialTheme.typography.h6)

                OutlinedTextField(
                    value = suplidor,
                    onValueChange = { suplidor = it },
                    label = { Text("Suplidor") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = ncf,
                    onValueChange = { ncf = it },
                    label = { Text("NCF") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = itbis,
                    onValueChange = { itbis = it },
                    label = { Text("ITBIS") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = monto,
                    onValueChange = { monto = it },
                    label = { Text("Monto") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val nuevo = Gasto(
                            gastoId = 0,
                            fecha = LocalDateTime.now().toString(),
                            suplidor = suplidor,
                            ncf = ncf,
                            itbis = itbis.toDoubleOrNull() ?: 0.0,
                            monto = monto.toDoubleOrNull() ?: 0.0
                        )
                        viewModel.enviarGasto(nuevo)

                        suplidor = ""
                        ncf = ""
                        itbis = ""
                        monto = ""

                        scope.launch { bottomSheetState.hide() }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar Gasto")
                }
            }
        }
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    scope.launch { bottomSheetState.show() }
                }, backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar Gasto")
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {

                Text("Lista de Gastos", style = MaterialTheme.typography.h6)

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(gastos) { gasto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = 2.dp
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("ID: ${gasto.gastoId}")
                                Text("Suplidor: ${gasto.suplidor}")
                                Text("NCF: ${gasto.ncf}")
                                Text("ITBIS: ${gasto.itbis}")
                                Text("Monto: ${gasto.monto}")
                                Text("Fecha: ${gasto.fecha}")
                            }
                        }
                    }
                }

                mensaje?.let {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("⚠️ $it", color = MaterialTheme.colors.error)
                }
            }
        }
    }
}
