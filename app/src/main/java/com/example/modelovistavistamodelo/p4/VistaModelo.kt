package com.example.modelovistavistamodelo.p4

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VistaModelo : ViewModel() {

    val cantidades = mutableStateListOf(0, 0, 0, 0)

    var mensaje by mutableStateOf("")
        private set

    fun cambiarCantidad(indice: Int, nueva: Int) {
        cantidades[indice] = nueva.coerceIn(0, 10)
        mensaje = ""
    }

    fun vaciar() {
        for (i in cantidades.indices) {
            cantidades[i] = 0
        }
    }

    fun confirmar() {
        vaciar()
        mensaje = "¡Pedido confirmado!"
    }

    fun articulos(): Int {
        return cantidades.sum()
    }

    fun subtotal(): Double {
        var suma = 0.0
        for (i in catalogo.indices) {
            suma = suma + catalogo[i].precio * cantidades[i]
        }
        return suma
    }

    fun descuento(): Double {
        if (subtotal() >= 200) {
            return subtotal() * 0.10
        }
        return 0.0
    }

    fun envio(): Double {
        if (articulos() == 0) {
            return 0.0
        }
        if (subtotal() - descuento() >= 150) {
            return 0.0
        }
        return 25.0
    }

    fun total(): Double {
        return subtotal() - descuento() + envio()
    }
}