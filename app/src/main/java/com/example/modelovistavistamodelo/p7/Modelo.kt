package com.example.modelovistavistamodelo.p7

const val CORREO_VALIDO = "paciente@vida.mx"
const val PASSWORD_VALIDO = "Vida2025"
const val MAX_INTENTOS = 3

enum class LoginEstado {
    INACTIVO,
    ERROR,
    BLOQUEADO,
    EXITO
}

data class Login(
    val email: String = "",
    val password: String = "",
    val recordarme: Boolean = false
)