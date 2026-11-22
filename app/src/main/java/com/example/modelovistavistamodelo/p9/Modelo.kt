package com.example.modelovistavistamodelo.p9

enum class Categoria(val texto: String) {
    COMIDA("Comida"),
    TRANSPORTE("Transporte"),
    RENTA("Renta"),
    OCIO("Ocio"),
    OTROS("Otros")
}

data class Gasto(
    val id: Int,
    val concepto: String,
    val monto: Double,
    val categoria: Categoria
)

fun dinero(valor: Double): String {
    return "$" + "%.2f".format(valor)
}