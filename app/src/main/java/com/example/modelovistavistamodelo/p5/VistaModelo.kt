package com.example.modelovistavistamodelo.p5

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.ceil

class VistaModelo : ViewModel() {

    var cuenta by mutableStateOf(Cuenta())
        private set

    fun cambiarMonto(valor: String) {
        cuenta = cuenta.copy(montoTexto = valor)
    }

    fun cambiarPropina(valor: Int) {
        cuenta = cuenta.copy(propinaPct = valor)
    }

    fun cambiarUsaOtro(valor: Boolean) {
        cuenta = cuenta.copy(usaOtro = valor)
    }

    fun cambiarPersonas(valor: Int) {
        cuenta = cuenta.copy(personas = valor.coerceIn(1, 20))
    }

    fun cambiarRedondear(valor: Boolean) {
        cuenta = cuenta.copy(redondear = valor)
    }

    fun montoValido(): Boolean {
        return cuenta.montoTexto.toDoubleOrNull() != null
    }

    fun monto(): Double {
        return cuenta.montoTexto.toDoubleOrNull() ?: 0.0
    }

    fun propinaTotal(): Double {
        return monto() * cuenta.propinaPct / 100
    }

    fun totalConPropina(): Double {
        return monto() + propinaTotal()
    }

    fun porPersona(): Double {
        val dividido = totalConPropina() / cuenta.personas
        if (cuenta.redondear) {
            return ceil(dividido)
        }
        return dividido
    }

    fun sobrante(): Double? {
        if (cuenta.redondear) {
            return porPersona() * cuenta.personas - totalConPropina()
        }
        return null
    }
}