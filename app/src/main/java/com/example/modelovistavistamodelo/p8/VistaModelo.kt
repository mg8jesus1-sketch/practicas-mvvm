package com.example.modelovistavistamodelo.p8

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VistaModelo : ViewModel() {

    var resena by mutableStateOf(Resena())
        private set

    var publicada by mutableStateOf(false)
        private set

    fun cambiarComida(valor: Int) {
        resena = resena.copy(comida = valor)
    }

    fun cambiarServicio(valor: Int) {
        resena = resena.copy(servicio = valor)
    }

    fun cambiarAmbiente(valor: Int) {
        resena = resena.copy(ambiente = valor)
    }

    fun cambiarComentario(texto: String) {
        resena = resena.copy(comentario = texto.take(MAX_COMENTARIO))
    }

    fun cambiarEtiqueta(etiqueta: String) {
        if (etiqueta in resena.etiquetas) {
            resena = resena.copy(etiquetas = resena.etiquetas - etiqueta)
        } else {
            resena = resena.copy(etiquetas = resena.etiquetas + etiqueta)
        }
    }

    fun general(): Double? {
        if (resena.comida == 0 || resena.servicio == 0 || resena.ambiente == 0) {
            return null
        }
        return (resena.comida + resena.servicio + resena.ambiente) / 3.0
    }

    fun puedePublicar(): Boolean {
        return general() != null && resena.comentario.length >= MIN_COMENTARIO
    }

    fun publicar() {
        if (puedePublicar()) {
            publicada = true
        }
    }

    fun nuevaResena() {
        resena = Resena()
        publicada = false
    }
}