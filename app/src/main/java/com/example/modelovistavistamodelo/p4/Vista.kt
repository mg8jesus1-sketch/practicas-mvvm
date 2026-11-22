package com.example.modelovistavistamodelo.p4

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun dinero(valor: Double): String {
    return "$" + "%.2f".format(valor)
}

@Composable
fun CarritoScreen(vm: VistaModelo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("Tienda Don Pepe", fontSize = 24.sp)

        Spacer(Modifier.height(16.dp))

        for (i in catalogo.indices) {
            ProductoItem(
                producto = catalogo[i],
                cantidad = vm.cantidades[i],
                onCantidadChange = { vm.cambiarCantidad(i, it) }
            )
        }

        Spacer(Modifier.height(16.dp))

        ResumenCarrito(
            articulos = vm.articulos(),
            subtotal = vm.subtotal(),
            descuento = vm.descuento(),
            envio = vm.envio(),
            total = vm.total()
        )

        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { vm.vaciar() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Vaciar")
            }
            Spacer(Modifier.width(12.dp))
            Button(
                onClick = { vm.confirmar() },
                enabled = vm.articulos() > 0,
                modifier = Modifier.weight(2f)
            ) {
                Text("Confirmar pedido")
            }
        }

        if (vm.mensaje != "") {
            Spacer(Modifier.height(12.dp))
            Text(vm.mensaje, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProductoItem(
    producto: Producto,
    cantidad: Int,
    onCantidadChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(producto.nombre, fontWeight = FontWeight.Bold)
            Text(dinero(producto.precio) + " c/u")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedButton(
                onClick = { onCantidadChange(cantidad - 1) },
                enabled = cantidad > 0
            ) {
                Text("-")
            }
            Text(
                text = "$cantidad",
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            OutlinedButton(
                onClick = { onCantidadChange(cantidad + 1) },
                enabled = cantidad < 10
            ) {
                Text("+")
            }
        }
    }
}

@Composable
fun ResumenCarrito(
    articulos: Int,
    subtotal: Double,
    descuento: Double,
    envio: Double,
    total: Double
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEEE8F6), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        FilaResumen("Artículos", "$articulos")
        FilaResumen("Subtotal", dinero(subtotal))
        FilaResumen("Descuento 10 %", "-" + dinero(descuento))
        if (envio == 0.0) {
            FilaResumen("Envío", "GRATIS")
        } else {
            FilaResumen("Envío", dinero(envio))
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("TOTAL", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(dinero(total), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun FilaResumen(titulo: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(titulo)
        Text(valor)
    }
}

@Preview(showBackground = true)
@Composable
fun CarritoScreenPreview() {
    MaterialTheme {
        CarritoScreen(VistaModelo())
    }
}

@Preview(showBackground = true)
@Composable
fun ProductoItemPreview() {
    MaterialTheme {
        ProductoItem(
            producto = Producto("Leche 1 L", 27.5),
            cantidad = 2,
            onCantidadChange = {}
        )
    }
}