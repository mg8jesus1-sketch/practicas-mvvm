package com.example.modelovistavistamodelo.p5

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun dinero(valor: Double): String {
    return "$" + "%.2f".format(valor)
}

@Composable
fun CuentaScreen(vm: VistaModelo) {
    val cuenta = vm.cuenta

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("Cuenta dividida", fontSize = 24.sp)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = cuenta.montoTexto,
            onValueChange = { vm.cambiarMonto(it) },
            label = { Text("Monto de la cuenta") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            supportingText = {
                if (!vm.montoValido()) {
                    Text("Monto no válido, se usa \$0.00")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        SelectorPropina(
            porcentaje = cuenta.propinaPct,
            usaOtro = cuenta.usaOtro,
            onPorcentajeChange = { vm.cambiarPropina(it) },
            onOtroChange = { vm.cambiarUsaOtro(it) }
        )

        Spacer(Modifier.height(16.dp))

        StepperPersonas(
            personas = cuenta.personas,
            onChange = { vm.cambiarPersonas(it) }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Redondear por persona")
            Switch(
                checked = cuenta.redondear,
                onCheckedChange = { vm.cambiarRedondear(it) }
            )
        }

        Spacer(Modifier.height(16.dp))

        ResultadoCuenta(
            porcentaje = cuenta.propinaPct,
            propina = vm.propinaTotal(),
            total = vm.totalConPropina(),
            porPersona = vm.porPersona(),
            personas = cuenta.personas,
            sobrante = vm.sobrante()
        )
    }
}

@Composable
fun SelectorPropina(
    porcentaje: Int,
    usaOtro: Boolean,
    onPorcentajeChange: (Int) -> Unit,
    onOtroChange: (Boolean) -> Unit
) {
    Column {
        Text("Propina")

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = !usaOtro && porcentaje == 10,
                onClick = {
                    onOtroChange(false)
                    onPorcentajeChange(10)
                },
                label = { Text("10 %") }
            )
            FilterChip(
                selected = !usaOtro && porcentaje == 15,
                onClick = {
                    onOtroChange(false)
                    onPorcentajeChange(15)
                },
                label = { Text("15 %") }
            )
            FilterChip(
                selected = !usaOtro && porcentaje == 20,
                onClick = {
                    onOtroChange(false)
                    onPorcentajeChange(20)
                },
                label = { Text("20 %") }
            )
            FilterChip(
                selected = usaOtro,
                onClick = { onOtroChange(true) },
                label = { Text("Otro") }
            )
        }

        if (usaOtro) {
            Text("Propina: $porcentaje %")
            Slider(
                value = porcentaje.toFloat(),
                onValueChange = { onPorcentajeChange(it.toInt()) },
                valueRange = 0f..30f
            )
        }
    }
}

@Composable
fun StepperPersonas(
    personas: Int,
    onChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Personas")
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedButton(
                onClick = { onChange(personas - 1) },
                enabled = personas > 1
            ) {
                Text("-")
            }
            Text(
                text = "$personas",
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            OutlinedButton(
                onClick = { onChange(personas + 1) },
                enabled = personas < 20
            ) {
                Text("+")
            }
        }
    }
}

@Composable
fun ResultadoCuenta(
    porcentaje: Int,
    propina: Double,
    total: Double,
    porPersona: Double,
    personas: Int,
    sobrante: Double?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEEE8F6), RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Propina ($porcentaje %)")
            Text(dinero(propina))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total con propina")
            Text(dinero(total))
        }

        Spacer(Modifier.height(16.dp))

        Text("Cada quien paga")
        Text(
            text = dinero(porPersona),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Text("entre $personas personas")

        if (sobrante != null) {
            Text("Sobrante por redondeo: " + dinero(sobrante))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CuentaScreenPreview() {
    MaterialTheme {
        CuentaScreen(VistaModelo())
    }
}

@Preview(showBackground = true)
@Composable
fun StepperPersonasPreview() {
    MaterialTheme {
        StepperPersonas(personas = 5, onChange = {})
    }
}