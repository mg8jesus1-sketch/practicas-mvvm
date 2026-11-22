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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AforoScreen(vm: VistaModelo) {
    val aforo = vm.aforo
    val porcentaje = vm.porcentaje()

    val color = if (porcentaje < 70) {
        Color(0xFF2E7D32)
    } else if (porcentaje < 100) {
        Color(0xFFF9A825)
    } else {
        Color(0xFFC62828)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Biblioteca Central", fontSize = 26.sp)
        Text("Sala de estudio · capacidad ${aforo.maximo}")

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${aforo.actuales} / ${aforo.maximo}",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text("personas en la sala")
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { porcentaje / 100f },
                color = color,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "$porcentaje % de ocupación",
                color = color,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { vm.sale() },
                enabled = aforo.actuales > 0,
                modifier = Modifier.weight(1f)
            ) {
                Text("- Sale")
            }
            Spacer(Modifier.width(12.dp))
            Button(
                onClick = { vm.entra() },
                enabled = aforo.actuales < aforo.maximo,
                modifier = Modifier.weight(1f)
            ) {
                Text("+ Entra")
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Visitas del día")
            Text(
                text = "${aforo.visitas}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            onClick = { vm.cerrarDia() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar día")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AforoScreenPreview() {
    MaterialTheme {
        AforoScreen(VistaModelo())
    }
}