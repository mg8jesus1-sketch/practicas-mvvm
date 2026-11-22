package com.example.modelovistavistamodelo.p10

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VistaModelo : ViewModel() {

    var pantalla by mutableStateOf(Pantalla.INICIO)
        private set

    var nombre by mutableStateOf("")
        private set

    var indicePregunta by mutableStateOf(0)
        private set

    var seleccionada by mutableStateOf<Int?>(null)
        private set

    var respondida by mutableStateOf(false)
        private set

    var puntaje by mutableStateOf(0)
        private set

    fun cambiarNombre(valor: String) {
        nombre = valor
    }

    fun nombreValido(): Boolean {
        return nombre.trim().length >= 2
    }

    private fun reiniciarQuiz() {
        indicePregunta = 0
        seleccionada = null
        respondida = false
        puntaje = 0
    }

    fun comenzar() {
        if (nombreValido()) {
            reiniciarQuiz()
            pantalla = Pantalla.QUIZ
        }
    }

    fun seleccionar(opcion: Int) {
        if (!respondida) {
            seleccionada = opcion
        }
    }

    fun confirmar() {
        if (seleccionada != null && !respondida) {
            respondida = true
            if (seleccionada == preguntaActual().correcta) {
                puntaje = puntaje + 1
            }
        }
    }

    fun siguiente() {
        if (esUltima()) {
            pantalla = Pantalla.RESULTADO
        } else {
            indicePregunta = indicePregunta + 1
            seleccionada = null
            respondida = false
        }
    }

    fun reintentar() {
        reiniciarQuiz()
        pantalla = Pantalla.QUIZ
    }

    fun cambiarJugador() {
        reiniciarQuiz()
        nombre = ""
        pantalla = Pantalla.INICIO
    }

    fun preguntaActual(): Pregunta {
        return preguntas[indicePregunta]
    }

    fun esUltima(): Boolean {
        return indicePregunta == preguntas.size - 1
    }

    fun mensajeResultado(): String {
        if (puntaje <= 2) {
            return "Sigue practicando"
        }
        if (puntaje <= 4) {
            return "Buen trabajo"
        }
        return "Perfecto"
    }
}