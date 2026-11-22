package com.example.mvvm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InscripcionScreen(vm: VistaModelo) {
    if (vm.inscrito) {
        ConfirmacionScreen(
            nombre = vm.datos.nombre,
            correo = vm.datos.correo,
            edad = vm.datos.edad,
            taller = vm.datos.taller,
            onNueva = { vm.nuevaInscripcion() }
        )
    } else {
        InscripcionForm(vm)
    }
}

@Composable
fun InscripcionForm(vm: VistaModelo) {
    val datos = vm.datos

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("Inscripción a talleres", fontSize = 24.sp)
        Text("Casa del Lago · cupo limitado")

        Spacer(Modifier.height(16.dp))

        CampoTexto(
            label = "Nombre completo",
            value = datos.nombre,
            onValueChange = { vm.cambiarNombre(it) },
            error = vm.errorNombre()
        )

        CampoTexto(
            label = "Correo",
            value = datos.correo,
            onValueChange = { vm.cambiarCorreo(it) },
            error = vm.errorCorreo(),
            teclado = KeyboardType.Email
        )

        CampoTexto(
            label = "Edad",
            value = datos.edad,
            onValueChange = { vm.cambiarEdad(it) },
            error = vm.errorEdad(),
            teclado = KeyboardType.Number
        )

        SelectorTaller(
            taller = datos.taller,
            onTallerChange = { vm.cambiarTaller(it) }
        )

        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = datos.aceptaAviso,
                onCheckedChange = { vm.cambiarAviso(it) }
            )
            Text("Acepto el aviso de privacidad")
        }
        val errorAviso = vm.errorAviso()
        if (errorAviso != null) {
            Text(errorAviso, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { vm.inscribirme() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Inscribirme")
        }

        Spacer(Modifier.height(8.dp))

        Text("Quedan ${lugaresDisponibles(datos.taller)} lugares en este taller")
    }
}

@Composable
fun CampoTexto(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    error: String?,
    teclado: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = error != null,
        supportingText = {
            if (error != null) {
                Text(error)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = teclado),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SelectorTaller(
    taller: String,
    onTallerChange: (String) -> Unit
) {
    var menuAbierto by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(
            onClick = { menuAbierto = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Taller: $taller")
        }
        DropdownMenu(
            expanded = menuAbierto,
            onDismissRequest = { menuAbierto = false }
        ) {
            for (opcion in talleres) {
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onTallerChange(opcion)
                        menuAbierto = false
                    }
                )
            }
        }
    }
}

@Composable
fun ConfirmacionScreen(
    nombre: String,
    correo: String,
    edad: String,
    taller: String,
    onNueva: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("¡Inscripción exitosa!", fontSize = 24.sp)

        Spacer(Modifier.height(16.dp))

        Text("Nombre: $nombre")
        Text("Correo: $correo")
        Text("Edad: $edad")
        Text("Taller: $taller")

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onNueva,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nueva inscripción")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InscripcionScreenPreview() {
    MaterialTheme {
        InscripcionScreen(VistaModelo())
    }
}