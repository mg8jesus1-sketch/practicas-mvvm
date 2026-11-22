package com.example.modelovistavistamodelo.p7

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VistaModelo : ViewModel() {

    var datos by mutableStateOf(Login())
        private set

    var intentosFallidos by mutableStateOf(0)
        private set

    var estado by mutableStateOf(LoginEstado.INACTIVO)
        private set

    fun cambiarEmail(valor: String) {
        datos = datos.copy(email = valor)
    }

    fun cambiarPassword(valor: String) {
        datos = datos.copy(password = valor)
    }

    fun cambiarRecordarme(valor: Boolean) {
        datos = datos.copy(recordarme = valor)
    }

    fun entrar() {
        if (estado == LoginEstado.BLOQUEADO) {
            return
        }
        if (datos.email == CORREO_VALIDO && datos.password == PASSWORD_VALIDO) {
            estado = LoginEstado.EXITO
        } else {
            intentosFallidos = intentosFallidos + 1
            if (intentosFallidos >= MAX_INTENTOS) {
                estado = LoginEstado.BLOQUEADO
            } else {
                estado = LoginEstado.ERROR
            }
        }
    }

    fun cerrarSesion() {
        datos = Login()
        intentosFallidos = 0
        estado = LoginEstado.INACTIVO
    }

    fun mensajeError(): String? {
        if (estado == LoginEstado.ERROR) {
            return "Correo o contraseña incorrectos (intento $intentosFallidos de $MAX_INTENTOS)"
        }
        if (estado == LoginEstado.BLOQUEADO) {
            return "Demasiados intentos fallidos. Inténtalo de nuevo más tarde."
        }
        return null
    }

    fun bloqueado(): Boolean {
        return estado == LoginEstado.BLOQUEADO
    }
}