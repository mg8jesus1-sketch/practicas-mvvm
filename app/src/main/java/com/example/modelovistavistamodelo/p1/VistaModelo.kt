package com.example.mvvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VistaModelo : ViewModel() {

    var aforo by mutableStateOf(Aforo())
        private set

    fun entra() {
        if (aforo.actuales < aforo.maximo) {
            aforo = aforo.copy(
                actuales = aforo.actuales + 1,
                visitas = aforo.visitas + 1
            )
        }
    }

    fun sale() {
        if (aforo.actuales > 0) {
            aforo = aforo.copy(actuales = aforo.actuales - 1)
        }
    }

    fun cerrarDia() {
        aforo = Aforo()
    }

    fun porcentaje(): Int {
        return aforo.actuales * 100 / aforo.maximo
    }
}