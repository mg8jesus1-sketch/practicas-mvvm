package com.example.mvvm

val talleres = listOf("Pintura", "Fotografía", "Guitarra")

data class Inscripcion(
    val nombre: String = "",
    val correo: String = "",
    val edad: String = "",
    val taller: String = "Pintura",
    val aceptaAviso: Boolean = false
)

fun validarNombre(nombre: String): String? {
    if (nombre.trim().length < 3) {
        return "Escribe al menos 3 caracteres"
    }
    return null
}

fun validarCorreo(correo: String): String? {
    if (!correo.contains("@")) {
        return "Ingresa un correo válido"
    }
    if (!correo.substringAfter("@").contains(".")) {
        return "Ingresa un correo válido"
    }
    return null
}

fun validarEdad(edad: String): String? {
    val numero = edad.toIntOrNull()
    if (numero == null || numero < 12 || numero > 99) {
        return "La edad debe estar entre 12 y 99"
    }
    return null
}

fun validarAviso(acepta: Boolean): String? {
    if (!acepta) {
        return "Debes aceptar el aviso de privacidad"
    }
    return null
}

fun lugaresDisponibles(taller: String): Int {
    return when (taller) {
        "Pintura" -> 8
        "Fotografía" -> 6
        else -> 10
    }
}