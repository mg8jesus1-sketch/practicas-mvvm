package com.example.modelovistavistamodelo.p4

data class Producto(
    val nombre: String,
    val precio: Double
)

val catalogo = listOf(
    Producto("Tortillas 1 kg", 24.0),
    Producto("Leche 1 L", 27.5),
    Producto("Huevo 12 pz", 52.0),
    Producto("Frijol 1 kg", 38.0)
)