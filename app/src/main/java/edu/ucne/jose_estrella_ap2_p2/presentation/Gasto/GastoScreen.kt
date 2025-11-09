package edu.ucne.jose_estrella_ap2_p2.presentation.Gasto

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import edu.ucne.jose_estrella_ap2_p2.domain.model.Gasto
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GastoScreen(viewModel: GastoViewModel) {
    val gastos by viewModel.gastos.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()
    val gastoEncontrado by viewModel.gastoEncontrado.collectAsState()

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    var modoEdicion by rememberSaveable { mutableStateOf(false) }
    var gastoEditandoId by rememberSaveable { mutableStateOf(0) }

    var suplidor by rememberSaveable { mutableStateOf("") }
    var ncf by rememberSaveable { mutableStateOf("") }
    var itbis by rememberSaveable { mutableStateOf("") }
    var monto by rememberSaveable { mutableStateOf("") }
    var buscarId by rememberSaveable { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.cargarGastos()
    }

    val listaFiltrada = if (buscarId.isNotBlank() && gastoEncontrado != null)
        listOf(gastoEncontrado!!)
    else
        gastos

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {

                Text(
                    text = if (modoEdicion) "Editar Gasto" else "Nuevo Gasto",
                    style = MaterialTheme.typography.h6
                )

                OutlinedTextField(
                    value = suplidor,
                    onValueChange = { suplidor = it },
                    label = { Text("Suplidor") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = ncf,
                    onValueChange = { ncf = it },
                    label = { Text("NCF") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = itbis,
                    onValueChange = { itbis = it },
                    label = { Text("ITBIS") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = monto,
                    onValueChange = { monto = it },
                    label = { Text("Monto") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = androidx.compose.foundation.text.KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val gasto = Gasto(
                            gastoId = gastoEditandoId,
                            fecha = LocalDateTime.now().toString(),
                            suplidor = suplidor,
                            ncf = ncf,
                            itbis = itbis.toDoubleOrNull() ?: 0.0,
                            monto = monto.toDoubleOrNull() ?: 0.0
                        )

                        if (modoEdicion) {
                            viewModel.actualizarGasto(gasto.gastoId, gasto)
                        } else {
                            viewModel.enviarGasto(gasto)
                        }

                        modoEdicion = false
                        gastoEditandoId = 0
                        suplidor = ""
                        ncf = ""
                        itbis = ""
                        monto = ""

                        scope.launch { bottomSheetState.hide() }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(if (modoEdicion) "Actualizar Gasto" else "Guardar Gasto")
                }
            }
        }
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        modoEdicion = false
                        gastoEditandoId = 0
                        suplidor = ""
                        ncf = ""
                        itbis = ""
                        monto = ""
                        scope.launch { bottomSheetState.show() }
                    },
                    backgroundColor = MaterialTheme.colors.primary
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
                Text("Lista de Gastos", style = MaterialTheme.typography.h6, fontSize = 22.sp)

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = buscarId,
                    onValueChange = {
                        buscarId = it
                        val id = it.toIntOrNull()
                        if (id != null) viewModel.obtenerGasto(id)
                        else viewModel.limpiarGastoEncontrado()
                    },
                    placeholder = { Text("Search") },
                    singleLine = true,
                    shape = RoundedCornerShape(50),
                    trailingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(listaFiltrada) { gasto ->
                        Card(
                            backgroundColor = MaterialTheme.colors.surface,
                            shape = RoundedCornerShape(12.dp),
                            elevation = 4.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("ID: ${gasto.gastoId}", fontWeight = FontWeight.Bold)
                                    Text("Suplidor: ${gasto.suplidor}")
                                    Text("NCF: ${gasto.ncf}")
                                    Text("ITBIS: ${gasto.itbis}")
                                    Text("Monto: ${gasto.monto}")
                                    Text("Fecha: ${gasto.fecha}")
                                }

                                IconButton(
                                    onClick = {
                                        modoEdicion = true
                                        gastoEditandoId = gasto.gastoId
                                        suplidor = gasto.suplidor
                                        ncf = gasto.ncf
                                        itbis = gasto.itbis.toString()
                                        monto = gasto.monto.toString()
                                        scope.launch { bottomSheetState.show() }
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp)
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                                }
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
