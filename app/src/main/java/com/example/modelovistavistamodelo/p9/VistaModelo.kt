package com.example.modelovistavistamodelo.p9

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VistaModelo : ViewModel() {

    var ingresoTexto by mutableStateOf("4500")
        private set

    val gastos = mutableStateListOf<Gasto>()

    private var siguienteId = 1

    fun cambiarIngreso(valor: String) {
        ingresoTexto = valor
    }

    fun agregarGasto(concepto: String, monto: Double, categoria: Categoria) {
        gastos.add(Gasto(siguienteId, concepto, monto, categoria))
        siguienteId = siguienteId + 1
    }

    fun eliminarGasto(gasto: Gasto) {
        gastos.remove(gasto)
    }

    fun ingreso(): Double {
        return ingresoTexto.toDoubleOrNull() ?: 0.0
    }

    fun total(): Double {
        return gastos.sumOf { it.monto }
    }

    fun saldo(): Double {
        return ingreso() - total()
    }

    fun porcentaje(): Double {
        if (ingreso() <= 0) {
            return 0.0
        }
        return total() / ingreso() * 100
    }

    fun excedido(): Double {
        return total() - ingreso()
    }

    fun categoriaTop(): String {
        val sumas = gastos
            .groupBy { it.categoria }
            .mapValues { entrada -> entrada.value.sumOf { it.monto } }
        val mayor = sumas.maxByOrNull { it.value }
        if (mayor == null) {
            return "Sin gastos"
        }
        return mayor.key.texto + " (" + dinero(mayor.value) + ")"
    }
}