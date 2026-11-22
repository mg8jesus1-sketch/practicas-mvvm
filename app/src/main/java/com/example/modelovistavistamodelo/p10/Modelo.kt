package com.example.modelovistavistamodelo.p10

enum class Pantalla {
    INICIO,
    QUIZ,
    RESULTADO
}

data class Pregunta(
    val texto: String,
    val opciones: List<String>,
    val correcta: Int
)

val preguntas = listOf(
    Pregunta(
        "¿Qué tipo de dato conviene usar para guardar el valor 3.14?",
        listOf("Int", "Double", "String", "Boolean"),
        1
    ),
    Pregunta(
        "¿Qué estructura repite un bloque de código varias veces?",
        listOf("if", "class", "for", "return"),
        2
    ),
    Pregunta(
        "¿Qué palabra de Kotlin declara una variable que no cambia?",
        listOf("val", "var", "fun", "let"),
        0
    ),
    Pregunta(
        "¿Cómo se llama un conjunto ordenado de elementos del mismo tipo?",
        listOf("Función", "Variable", "Condición", "Arreglo"),
        3
    ),
    Pregunta(
        "¿Cuál es el resultado de 7 % 2?",
        listOf("0", "1", "3", "3.5"),
        1
    )
)