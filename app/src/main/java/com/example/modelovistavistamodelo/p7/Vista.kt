package com.example.modelovistavistamodelo.p7

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(vm: VistaModelo) {
    when (vm.estado) {
        LoginEstado.EXITO -> HomeScreen(
            email = vm.datos.email,
            onLogout = { vm.cerrarSesion() }
        )
        else -> LoginForm(
            email = vm.datos.email,
            onEmailChange = { vm.cambiarEmail(it) },
            password = vm.datos.password,
            onPasswordChange = { vm.cambiarPassword(it) },
            recordarme = vm.datos.recordarme,
            onRecordarmeChange = { vm.cambiarRecordarme(it) },
            mensajeError = vm.mensajeError(),
            bloqueado = vm.bloqueado(),
            onLogin = { vm.entrar() }
        )
    }
}

@Composable
fun LoginForm(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    recordarme: Boolean,
    onRecordarmeChange: (Boolean) -> Unit,
    mensajeError: String?,
    bloqueado: Boolean,
    onLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        Text("Clínica Vida", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Consulta tus citas médicas")

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Correo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        PasswordField(
            value = password,
            onValueChange = onPasswordChange
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = recordarme,
                onCheckedChange = onRecordarmeChange
            )
            Text("Recordarme")
        }

        if (mensajeError != null) {
            Text(
                text = mensajeError,
                color = Color(0xFFB71C1C),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFADBD8), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            )
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = onLogin,
            enabled = !bloqueado,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }
    }
}

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var mostrarPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Contraseña") },
        singleLine = true,
        visualTransformation = if (mostrarPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            TextButton(onClick = { mostrarPassword = !mostrarPassword }) {
                Text(if (mostrarPassword) "Ocultar" else "Ver")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun HomeScreen(
    email: String,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        Text("¡Bienvenido!", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text(email)

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginFormPreview() {
    MaterialTheme {
        LoginForm(
            email = "paciente@vida.mx",
            onEmailChange = {},
            password = "12345678",
            onPasswordChange = {},
            recordarme = true,
            onRecordarmeChange = {},
            mensajeError = "Correo o contraseña incorrectos (intento 2 de 3)",
            bloqueado = false,
            onLogin = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
            email = "paciente@vida.mx",
            onLogout = {}
        )
    }
}