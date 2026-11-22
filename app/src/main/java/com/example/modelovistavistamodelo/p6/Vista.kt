package com.example.modelovistavistamodelo.p6

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TareasScreen(vm: VistaModelo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Mis pendientes", fontSize = 24.sp)

        Spacer(Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = vm.textoNueva,
                onValueChange = { vm.cambiarTexto(it) },
                label = { Text("Nueva tarea") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = { vm.agregar() },
                enabled = vm.textoNueva.isNotBlank()
            ) {
                Text("+")
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Prioridad")
            for (p in Prioridad.values()) {
                FilterChip(
                    selected = vm.prioridadNueva == p,
                    onClick = { vm.cambiarPrioridad(p) },
                    label = { Text(p.texto) }
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = vm.filtro == Filtro.TODAS,
                onClick = { vm.cambiarFiltro(Filtro.TODAS) },
                label = { Text("Todas") }
            )
            FilterChip(
                selected = vm.filtro == Filtro.PENDIENTES,
                onClick = { vm.cambiarFiltro(Filtro.PENDIENTES) },
                label = { Text("Pendientes") }
            )
            FilterChip(
                selected = vm.filtro == Filtro.HECHAS,
                onClick = { vm.cambiarFiltro(Filtro.HECHAS) },
                label = { Text("Hechas") }
            )
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(vm.tareasVisibles(), key = { it.id }) { tarea ->
                TareaItem(
                    tarea = tarea,
                    onCheckedChange = { vm.marcar(tarea.id, it) },
                    onDelete = { vm.eliminar(tarea.id) }
                )
            }
        }

        Text(
            text = "${vm.pendientes()} pendientes de ${vm.tareas.size}",
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = { vm.pedirBorrarCompletadas() },
            enabled = vm.cantidadHechas() > 0,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Borrar completadas")
        }
    }

    if (vm.mostrarDialogo) {
        ConfirmarBorradoDialog(
            cantidad = vm.cantidadHechas(),
            onConfirm = { vm.confirmarBorrado() },
            onDismiss = { vm.cancelarBorrado() }
        )
    }
}

@Composable
fun TareaItem(
    tarea: Tarea,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    val colorPrioridad = when (tarea.prioridad) {
        Prioridad.ALTA -> Color(0xFFFFCDD2)
        Prioridad.MEDIA -> Color(0xFFFFF59D)
        Prioridad.BAJA -> Color(0xFFC8E6C9)
    }

    val decoracion = if (tarea.hecha) TextDecoration.LineThrough else TextDecoration.None

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = tarea.hecha,
            onCheckedChange = onCheckedChange
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(tarea.titulo, textDecoration = decoracion)
            Text(
                text = tarea.prioridad.texto,
                fontSize = 12.sp,
                modifier = Modifier
                    .background(colorPrioridad, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            )
        }
        TextButton(onClick = onDelete) {
            Text("X")
        }
    }
}

@Composable
fun ConfirmarBorradoDialog(
    cantidad: Int,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Borrar completadas") },
        text = { Text("Se van a borrar $cantidad tareas hechas. ¿Continuar?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Borrar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TareasScreenPreview() {
    MaterialTheme {
        TareasScreen(VistaModelo())
    }
}

@Preview(showBackground = true)
@Composable
fun TareaItemPreview() {
    MaterialTheme {
        TareaItem(
            tarea = Tarea(1, "Entregar proyecto POO", Prioridad.ALTA),
            onCheckedChange = {},
            onDelete = {}
        )
    }
}