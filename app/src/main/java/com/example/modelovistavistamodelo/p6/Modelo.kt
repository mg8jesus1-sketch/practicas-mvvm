package com.example.modelovistavistamodelo.p6

enum class Prioridad(val texto: String) {
    ALTA("Alta"),
    MEDIA("Media"),
    BAJA("Baja")
}

enum class Filtro {
    TODAS,
    PENDIENTES,
    HECHAS
}

data class Tarea(
    val id: Int,
    val titulo: String,
    val prioridad: Prioridad,
    val hecha: Boolean = false
)