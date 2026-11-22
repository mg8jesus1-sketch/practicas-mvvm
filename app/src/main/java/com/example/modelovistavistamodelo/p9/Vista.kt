package com.example.modelovistavistamodelo.p9

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PresupuestoScreen(vm: VistaModelo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("Mi presupuesto", fontSize = 24.sp)

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = vm.ingresoTexto,
            onValueChange = { vm.cambiarIngreso(it) },
            label = { Text("Ingreso mensual") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        ResumenPresupuesto(
            total = vm.total(),
            saldo = vm.saldo(),
            porcentaje = vm.porcentaje(),
            categoriaTop = vm.categoriaTop(),
            excedido = vm.excedido()
        )

        Spacer(Modifier.height(16.dp))

        Text("Nuevo gasto", fontWeight = FontWeight.Bold)

        FormularioGasto(
            onAgregar = { concepto, monto, categoria ->
                vm.agregarGasto(concepto, monto, categoria)
            }
        )

        Spacer(Modifier.height(16.dp))

        Text("Gastos recientes", fontWeight = FontWeight.Bold)

        ListaGastos(
            gastos = vm.gastos.reversed(),
            onEliminar = { vm.eliminarGasto(it) }
        )
    }
}

@Composable
fun ResumenPresupuesto(
    total: Double,
    saldo: Double,
    porcentaje: Double,
    categoriaTop: String,
    excedido: Double
) {
    val color = if (porcentaje > 100) {
        Color(0xFFC62828)
    } else if (porcentaje >= 80) {
        Color(0xFFF9A825)
    } else {
        Color(0xFF2E7D32)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Gastado")
                Text(dinero(total), fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
            Column {
                Text("Saldo")
                Text(dinero(saldo), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = color)
            }
        }

        Spacer(Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { (porcentaje / 100).coerceIn(0.0, 1.0).toFloat() },
            color = color,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Text("${porcentaje.toInt()} % usado · Mayor gasto: $categoriaTop")

        if (porcentaje > 100) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Te pasaste del presupuesto por " + dinero(excedido),
                color = color,
                fontWeight = FontWeight.Bold
            )
        } else if (porcentaje >= 80) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Cuidado: pasaste el 80 %",
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FormularioGasto(
    onAgregar: (String, Double, Categoria) -> Unit
) {
    var concepto by remember { mutableStateOf("") }
    var montoTexto by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf(Categoria.COMIDA) }
    var menuAbierto by remember { mutableStateOf(false) }

    val monto = montoTexto.toDoubleOrNull() ?: 0.0

    Column {
        OutlinedTextField(
            value = concepto,
            onValueChange = { concepto = it },
            label = { Text("Concepto") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = montoTexto,
                onValueChange = { montoTexto = it },
                label = { Text("Monto") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Box(modifier = Modifier.weight(1f)) {
                OutlinedButton(
                    onClick = { menuAbierto = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(categoria.texto)
                }
                DropdownMenu(
                    expanded = menuAbierto,
                    onDismissRequest = { menuAbierto = false }
                ) {
                    for (opcion in Categoria.values()) {
                        DropdownMenuItem(
                            text = { Text(opcion.texto) },
                            onClick = {
                                categoria = opcion
                                menuAbierto = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                onAgregar(concepto.trim(), monto, categoria)
                concepto = ""
                montoTexto = ""
            },
            enabled = concepto.isNotBlank() && monto > 0,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar gasto")
        }
    }
}

@Composable
fun ListaGastos(
    gastos: List<Gasto>,
    onEliminar: (Gasto) -> Unit
) {
    Column {
        for (gasto in gastos) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(gasto.concepto, fontWeight = FontWeight.Bold)
                    Text(gasto.categoria.texto)
                }
                Text("-" + dinero(gasto.monto), color = Color(0xFFC62828))
                TextButton(onClick = { onEliminar(gasto) }) {
                    Text("X")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PresupuestoScreenPreview() {
    MaterialTheme {
        PresupuestoScreen(VistaModelo())
    }
}

@Preview(showBackground = true)
@Composable
fun ResumenPresupuestoPreview() {
    MaterialTheme {
        ResumenPresupuesto(
            total = 3780.0,
            saldo = 720.0,
            porcentaje = 84.0,
            categoriaTop = "Renta ($2,000.00)",
            excedido = 0.0
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListaGastosPreview() {
    MaterialTheme {
        ListaGastos(
            gastos = listOf(
                Gasto(1, "Renta mensual", 2000.0, Categoria.RENTA),
                Gasto(2, "Despensa", 650.0, Categoria.COMIDA)
            ),
            onEliminar = {}
        )
    }
}