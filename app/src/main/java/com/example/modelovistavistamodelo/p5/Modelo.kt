package com.example.modelovistavistamodelo.p5

data class Cuenta(
    val montoTexto: String = "",
    val propinaPct: Int = 20,
    val usaOtro: Boolean = false,
    val personas: Int = 1,
    val redondear: Boolean = false
)