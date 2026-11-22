package com.example.modelovistavistamodelo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import com.example.modelovistavistamodelo.p4.CarritoScreen
import com.example.modelovistavistamodelo.p4.VistaModelo

class MainActivity : ComponentActivity() {

    private val vm: VistaModelo by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CarritoScreen(vm)
            }
        }
    }
}