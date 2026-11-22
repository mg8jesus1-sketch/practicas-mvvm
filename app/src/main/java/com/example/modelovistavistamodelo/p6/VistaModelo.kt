package com.example.modelovistavistamodelo.p6

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VistaModelo : ViewModel() {

    val tareas = mutableStateListOf<Tarea>()

    var textoNueva by mutableStateOf("")
        private set

    var prioridadNueva by mutableStateOf(Prioridad.ALTA)
        private set

    var filtro by mutableStateOf(Filtro.TODAS)
        private set

    var mostrarDialogo by mutableStateOf(false)
        private set

    private var siguienteId = 1

    fun cambiarTexto(valor: String) {
        textoNueva = valor
    }

    fun cambiarPrioridad(valor: Prioridad) {
        prioridadNueva = valor
    }

    fun cambiarFiltro(valor: Filtro) {
        filtro = valor
    }

    fun agregar() {
        if (textoNueva.isBlank()) {
            return
        }
        tareas.add(Tarea(siguienteId, textoNueva.trim(), prioridadNueva))
        siguienteId = siguienteId + 1
        textoNueva = ""
    }

    fun marcar(id: Int, hecha: Boolean) {
        for (i in tareas.indices) {
            if (tareas[i].id == id) {
                tareas[i] = tareas[i].copy(hecha = hecha)
            }
        }
    }

    fun eliminar(id: Int) {
        tareas.removeAll { it.id == id }
    }

    fun pedirBorrarCompletadas() {
        mostrarDialogo = true
    }

    fun cancelarBorrado() {
        mostrarDialogo = false
    }

    fun confirmarBorrado() {
        tareas.removeAll { it.hecha }
        mostrarDialogo = false
    }

    fun tareasVisibles(): List<Tarea> {
        return when (filtro) {
            Filtro.TODAS -> tareas.toList()
            Filtro.PENDIENTES -> tareas.filter { !it.hecha }
            Filtro.HECHAS -> tareas.filter { it.hecha }
        }
    }

    fun pendientes(): Int {
        return tareas.count { !it.hecha }
    }

    fun cantidadHechas(): Int {
        return tareas.count { it.hecha }
    }
}