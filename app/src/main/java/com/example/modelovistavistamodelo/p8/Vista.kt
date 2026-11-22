package com.example.modelovistavistamodelo.p8

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
fun ResenaScreen(vm: VistaModelo) {
    if (vm.publicada) {
        ResumenResena(
            general = vm.general() ?: 0.0,
            comentario = vm.resena.comentario,
            etiquetas = vm.resena.etiquetas,
            onNueva = { vm.nuevaResena() }
        )
    } else {
        ResenaForm(vm)
    }
}

@Composable
fun ResenaForm(vm: VistaModelo) {
    val resena = vm.resena
    val general = vm.general()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("Escribe tu reseña", fontSize = 24.sp)
        Text("Restaurante El Mezquite", fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(16.dp))

        FilaCalificacion(
            titulo = "Comida",
            rating = resena.comida,
            onRatingChange = { vm.cambiarComida(it) }
        )
        FilaCalificacion(
            titulo = "Servicio",
            rating = resena.servicio,
            onRatingChange = { vm.cambiarServicio(it) }
        )
        FilaCalificacion(
            titulo = "Ambiente",
            rating = resena.ambiente,
            onRatingChange = { vm.cambiarAmbiente(it) }
        )

        if (general != null) {
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEEE8F6), RoundedCornerShape(12.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Calificación general")
                Text("%.1f ★".format(general), fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.height(16.dp))

        ComentarioField(
            texto = resena.comentario,
            onChange = { vm.cambiarComentario(it) }
        )

        Spacer(Modifier.height(8.dp))

        for (fila in etiquetasDisponibles.chunked(3)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (etiqueta in fila) {
                    FilterChip(
                        selected = etiqueta in resena.etiquetas,
                        onClick = { vm.cambiarEtiqueta(etiqueta) },
                        label = { Text(etiqueta) }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { vm.publicar() },
            enabled = vm.puedePublicar(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Publicar")
        }
    }
}

@Composable
fun FilaCalificacion(
    titulo: String,
    rating: Int,
    onRatingChange: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(titulo)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingBar(rating = rating, onRatingChange = onRatingChange)
            Text(textoCalificacion(rating), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun RatingBar(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (i in 1..5) {
            val color = if (i <= rating) Color(0xFFFFA000) else Color(0xFFBDBDBD)
            Text(
                text = "★",
                fontSize = 36.sp,
                color = color,
                modifier = Modifier.clickable { onRatingChange(i) }
            )
        }
    }
}

@Composable
fun ComentarioField(
    texto: String,
    onChange: (String) -> Unit,
    maximo: Int = MAX_COMENTARIO
) {
    val colorContador = if (texto.length >= LIMITE_ROJO) {
        Color.Red
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    OutlinedTextField(
        value = texto,
        onValueChange = onChange,
        label = { Text("Comentario") },
        minLines = 3,
        supportingText = {
            Text(
                text = "${texto.length}/$maximo",
                color = colorContador
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ResumenResena(
    general: Double,
    comentario: String,
    etiquetas: Set<String>,
    onNueva: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("¡Reseña publicada!", fontSize = 24.sp)

        Spacer(Modifier.height(16.dp))

        Text("Calificación general: %.1f ★".format(general), fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(comentario)
        Spacer(Modifier.height(8.dp))
        Text("Etiquetas: " + etiquetas.joinToString(", "))

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onNueva,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nueva reseña")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResenaScreenPreview() {
    MaterialTheme {
        ResenaScreen(VistaModelo())
    }
}

@Preview(showBackground = true)
@Composable
fun RatingBarPreview() {
    MaterialTheme {
        RatingBar(rating = 4, onRatingChange = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ResumenResenaPreview() {
    MaterialTheme {
        ResumenResena(
            general = 4.0,
            comentario = "La carne estuvo excelente y el servicio fue muy rápido.",
            etiquetas = setOf("Familiar", "Económico"),
            onNueva = {}
        )
    }
}