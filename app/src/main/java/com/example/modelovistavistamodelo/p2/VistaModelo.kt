package com.example.mvvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VistaModelo : ViewModel() {

    var ajustes by mutableStateOf(Ajustes())
        private set

    fun cambiarTamano(valor: Float) {
        ajustes = ajustes.copy(tamano = valor)
    }

    fun cambiarInterlineado(valor: Interlineado) {
        ajustes = ajustes.copy(interlineado = valor)
    }

    fun cambiarModoOscuro(valor: Boolean) {
        ajustes = ajustes.copy(modoOscuro = valor)
    }

    fun cambiarFuente(usaSerif: Boolean) {
        ajustes = ajustes.copy(usaSerif = usaSerif)
    }

    fun restablecer() {
        ajustes = Ajustes()
    }
}