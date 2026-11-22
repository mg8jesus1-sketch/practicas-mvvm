package com.example.modelovistavistamodelo.p10

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
fun QuizApp(vm: VistaModelo) {
    when (vm.pantalla) {
        Pantalla.INICIO -> InicioScreen(
            nombre = vm.nombre,
            onNombreChange = { vm.cambiarNombre(it) },
            nombreValido = vm.nombreValido(),
            onComenzar = { vm.comenzar() }
        )
        Pantalla.QUIZ -> PreguntaScreen(
            pregunta = vm.preguntaActual(),
            numero = vm.indicePregunta + 1,
            total = preguntas.size,
            puntaje = vm.puntaje,
            seleccionada = vm.seleccionada,
            respondida = vm.respondida,
            onSeleccionar = { vm.seleccionar(it) },
            onConfirmar = { vm.confirmar() },
            onSiguiente = { vm.siguiente() },
            esUltima = vm.esUltima()
        )
        Pantalla.RESULTADO -> ResultadoScreen(
            nombre = vm.nombre,
            puntaje = vm.puntaje,
            total = preguntas.size,
            mensaje = vm.mensajeResultado(),
            onReintentar = { vm.reintentar() },
            onCambiarJugador = { vm.cambiarJugador() }
        )
    }
}

@Composable
fun InicioScreen(
    nombre: String,
    onNombreChange: (String) -> Unit,
    nombreValido: Boolean,
    onComenzar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Spacer(Modifier.height(48.dp))

        Text("Quiz de programación", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("5 preguntas de fundamentos")

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Tu nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onComenzar,
            enabled = nombreValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Comenzar")
        }
    }
}

@Composable
fun PreguntaScreen(
    pregunta: Pregunta,
    numero: Int,
    total: Int,
    puntaje: Int,
    seleccionada: Int?,
    respondida: Boolean,
    onSeleccionar: (Int) -> Unit,
    onConfirmar: () -> Unit,
    onSiguiente: () -> Unit,
    esUltima: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Pregunta $numero de $total")
            Text("Puntaje: $puntaje", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { numero / total.toFloat() },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Text(pregunta.texto, fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(16.dp))

        for (i in pregunta.opciones.indices) {
            var fondo = Color.Transparent
            if (respondida && i == pregunta.correcta) {
                fondo = Color(0xFFC8E6C9)
            } else if (respondida && i == seleccionada) {
                fondo = Color(0xFFFFCDD2)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .background(fondo, RoundedCornerShape(12.dp))
                    .clickable(enabled = !respondida) { onSeleccionar(i) }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = seleccionada == i,
                    onClick = null
                )
                Spacer(Modifier.width(8.dp))
                Text(pregunta.opciones[i])
            }
        }

        if (respondida) {
            Spacer(Modifier.height(8.dp))
            if (seleccionada == pregunta.correcta) {
                Text("¡Correcto!", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
            } else {
                Text(
                    text = "Incorrecto. La respuesta correcta es " + pregunta.opciones[pregunta.correcta] + ".",
                    color = Color(0xFFC62828),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = onConfirmar,
                enabled = seleccionada != null && !respondida,
                modifier = Modifier.weight(1f)
            ) {
                Text("Confirmar")
            }
            Spacer(Modifier.width(12.dp))
            Button(
                onClick = onSiguiente,
                enabled = respondida,
                modifier = Modifier.weight(1f)
            ) {
                Text(if (esUltima) "Ver resultados" else "Siguiente")
            }
        }
    }
}

@Composable
fun ResultadoScreen(
    nombre: String,
    puntaje: Int,
    total: Int,
    mensaje: String,
    onReintentar: () -> Unit,
    onCambiarJugador: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Spacer(Modifier.height(48.dp))

        Text("¡Muy bien, $nombre!", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("$puntaje de $total", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Text(mensaje, fontSize = 20.sp)

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onReintentar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reintentar")
        }

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = onCambiarJugador,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cambiar jugador")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InicioScreenPreview() {
    MaterialTheme {
        InicioScreen(
            nombre = "Ana",
            onNombreChange = {},
            nombreValido = true,
            onComenzar = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreguntaScreenPreview() {
    MaterialTheme {
        PreguntaScreen(
            pregunta = preguntas[0],
            numero = 3,
            total = 5,
            puntaje = 2,
            seleccionada = 0,
            respondida = true,
            onSeleccionar = {},
            onConfirmar = {},
            onSiguiente = {},
            esUltima = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultadoScreenPreview() {
    MaterialTheme {
        ResultadoScreen(
            nombre = "Ana",
            puntaje = 4,
            total = 5,
            mensaje = "Buen trabajo",
            onReintentar = {},
            onCambiarJugador = {}
        )
    }
}