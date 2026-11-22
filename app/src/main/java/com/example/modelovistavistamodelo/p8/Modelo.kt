package com.example.modelovistavistamodelo.p8

const val MAX_COMENTARIO = 200
const val MIN_COMENTARIO = 20
const val LIMITE_ROJO = 180

val etiquetasDisponibles = listOf("Familiar", "Romántico", "Económico", "Vegetariano", "Rápido")

data class Resena(
    val comida: Int = 0,
    val servicio: Int = 0,
    val ambiente: Int = 0,
    val comentario: String = "",
    val etiquetas: Set<String> = setOf()
)

fun textoCalificacion(estrellas: Int): String {
    return when (estrellas) {
        1 -> "Malo"
        2 -> "Regular"
        3 -> "Bueno"
        4 -> "Muy bueno"
        5 -> "Excelente"
        else -> ""
    }
}