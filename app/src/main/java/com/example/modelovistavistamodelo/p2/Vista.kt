package com.example.mvvm

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AjustesScreen(vm: VistaModelo) {
    val ajustes = vm.ajustes

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("Ajustes de lectura", fontSize = 24.sp)

        Spacer(Modifier.height(16.dp))

        Controles(
            tamano = ajustes.tamano,
            onTamanoChange = { vm.cambiarTamano(it) },
            interlineado = ajustes.interlineado,
            onInterlineadoChange = { vm.cambiarInterlineado(it) },
            modoOscuro = ajustes.modoOscuro,
            onModoOscuroChange = { vm.cambiarModoOscuro(it) },
            usaSerif = ajustes.usaSerif,
            onUsaSerifChange = { vm.cambiarFuente(it) }
        )

        Spacer(Modifier.height(16.dp))

        Text("VISTA PREVIA")
        Spacer(Modifier.height(8.dp))

        VistaPrevia(
            texto = "Era una noche tranquila en el pueblo cuando las campanas comenzaron a sonar, y nadie supo por qué.",
            tamano = ajustes.tamano,
            interlineado = ajustes.interlineado,
            modoOscuro = ajustes.modoOscuro,
            usaSerif = ajustes.usaSerif
        )

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            onClick = { vm.restablecer() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Restablecer")
        }
    }
}

@Composable
fun Controles(
    tamano: Float,
    onTamanoChange: (Float) -> Unit,
    interlineado: Interlineado,
    onInterlineadoChange: (Interlineado) -> Unit,
    modoOscuro: Boolean,
    onModoOscuroChange: (Boolean) -> Unit,
    usaSerif: Boolean,
    onUsaSerifChange: (Boolean) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Tamaño de letra")
            Text("${tamano.toInt()} sp")
        }
        Slider(
            value = tamano,
            onValueChange = onTamanoChange,
            valueRange = 14f..32f
        )

        Text("Interlineado")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = interlineado == Interlineado.COMPACTO,
                onClick = { onInterlineadoChange(Interlineado.COMPACTO) }
            )
            Text("Compacto")
            RadioButton(
                selected = interlineado == Interlineado.NORMAL,
                onClick = { onInterlineadoChange(Interlineado.NORMAL) }
            )
            Text("Normal")
            RadioButton(
                selected = interlineado == Interlineado.AMPLIO,
                onClick = { onInterlineadoChange(Interlineado.AMPLIO) }
            )
            Text("Amplio")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Modo oscuro")
            Switch(
                checked = modoOscuro,
                onCheckedChange = onModoOscuroChange
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Fuente")
            Row {
                FilterChip(
                    selected = usaSerif,
                    onClick = { onUsaSerifChange(true) },
                    label = { Text("Serif") }
                )
                Spacer(Modifier.padding(4.dp))
                FilterChip(
                    selected = !usaSerif,
                    onClick = { onUsaSerifChange(false) },
                    label = { Text("Sans") }
                )
            }
        }
    }
}

@Composable
fun VistaPrevia(
    texto: String,
    tamano: Float,
    interlineado: Interlineado,
    modoOscuro: Boolean,
    usaSerif: Boolean
) {
    val fondo = if (modoOscuro) Color(0xFF1C1B1F) else Color.White
    val colorTexto = if (modoOscuro) Color.White else Color.Black
    val fuente = if (usaSerif) FontFamily.Serif else FontFamily.SansSerif

    Text(
        text = texto,
        color = colorTexto,
        fontSize = tamano.sp,
        lineHeight = (tamano * interlineado.factor).sp,
        fontFamily = fuente,
        modifier = Modifier
            .fillMaxWidth()
            .background(fondo, RoundedCornerShape(16.dp))
            .padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun AjustesScreenPreview() {
    MaterialTheme {
        AjustesScreen(VistaModelo())
    }
}