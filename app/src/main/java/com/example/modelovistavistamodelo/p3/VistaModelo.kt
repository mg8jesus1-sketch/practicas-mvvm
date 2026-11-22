package com.example.mvvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VistaModelo : ViewModel() {

    var datos by mutableStateOf(Inscripcion())
        private set

    var intentoEnviar by mutableStateOf(false)
        private set

    var inscrito by mutableStateOf(false)
        private set

    fun cambiarNombre(valor: String) {
        datos = datos.copy(nombre = valor)
    }

    fun cambiarCorreo(valor: String) {
        datos = datos.copy(correo = valor)
    }

    fun cambiarEdad(valor: String) {
        datos = datos.copy(edad = valor.filter { it.isDigit() })
    }

    fun cambiarTaller(valor: String) {
        datos = datos.copy(taller = valor)
    }

    fun cambiarAviso(valor: Boolean) {
        datos = datos.copy(aceptaAviso = valor)
    }

    fun errorNombre(): String? {
        if (!intentoEnviar) return null
        return validarNombre(datos.nombre)
    }

    fun errorCorreo(): String? {
        if (!intentoEnviar) return null
        return validarCorreo(datos.correo)
    }

    fun errorEdad(): String? {
        if (!intentoEnviar) return null
        return validarEdad(datos.edad)
    }

    fun errorAviso(): String? {
        if (!intentoEnviar) return null
        return validarAviso(datos.aceptaAviso)
    }

    fun inscribirme() {
        intentoEnviar = true
        val todoValido = validarNombre(datos.nombre) == null &&
                validarCorreo(datos.correo) == null &&
                validarEdad(datos.edad) == null &&
                validarAviso(datos.aceptaAviso) == null
        if (todoValido) {
            inscrito = true
        }
    }

    fun nuevaInscripcion() {
        datos = Inscripcion()
        intentoEnviar = false
        inscrito = false
    }
}