package com.example.resistenciajeckpackc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.resistenciajeckpackc.ui.theme.TemaResistenciaC

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplicar el tema personalizado aquí
            TemaResistenciaC {
                resistenciajeckpackcApp()
            }
        }
    }
}

@Composable
fun resistenciajeckpackcApp() {
    var banda1 by remember { mutableStateOf("Marrón") }
    var banda2 by remember { mutableStateOf("Negro") }
    var banda3 by remember { mutableStateOf("Rojo") }
    var tolerancia by remember { mutableStateOf("Dorado") }
    var resultado by remember { mutableStateOf("") }

    val colorOptions = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
    val toleranciaOptions = listOf("Dorado", "Plateado", "Marrón", "Rojo", "Verde", "Azul", "Violeta", "Gris")

    fun calcular() {
        resultado = calcularResistencia(banda1, banda2, banda3, tolerancia)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calculadora de Resistencias",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        // Selección de bandas de colores
        ColorDropdown("Banda 1", colorOptions, banda1) { newValue ->
            banda1 = newValue
            calcular()
        }
        ColorDropdown("Banda 2", colorOptions, banda2) { newValue ->
            banda2 = newValue
            calcular()
        }
        ColorDropdown("Banda 3 (Multiplicador)", colorOptions, banda3) { newValue ->
            banda3 = newValue
            calcular()
        }

        // Selección de tolerancia
        ColorDropdown("Tolerancia", toleranciaOptions, tolerancia) { newValue ->
            tolerancia = newValue
            calcular()
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el resultado
        Text(
            text = "El Resultado es = $resultado",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

}

// Mapa de colores asociados a sus valores hexadecimales
val colorMap = mapOf(
    "Negro" to Color(0xFF000000),
    "Marrón" to Color(0xFFA52A2A),
    "Rojo" to Color(0xFFFF0000),
    "Naranja" to Color(0xFFFFA500),
    "Amarillo" to Color(0xFFFFFF00),
    "Verde" to Color(0xFF008000),
    "Azul" to Color(0xFF0000FF),
    "Violeta" to Color(0xFF8A2BE2),
    "Gris" to Color(0xFF808080),
    "Blanco" to Color(0xFFFFFFFF),
    "Dorado" to Color(0xFFFFD700),
    "Plateado" to Color(0xFFC0C0C0)
)

@Composable
fun ColorDropdown(label: String, items: List<String>, selectedItem: String, onItemSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = selectedItem,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            // Cuadro de color
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(colorMap[item] ?: Color.Transparent)
                                    .padding(end = 8.dp)
                            )
                            Text(text = item)
                        }
                    },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

fun calcularResistencia(banda1: String, banda2: String, banda3: String, tolerancia: String?): String {
    val colorValor = mapOf(
        "Negro" to 0,
        "Marrón" to 1,
        "Rojo" to 2,
        "Naranja" to 3,
        "Amarillo" to 4,
        "Verde" to 5,
        "Azul" to 6,
        "Violeta" to 7,
        "Gris" to 8,
        "Blanco" to 9
    )

    val colorMultiplicador = mapOf(
        "Negro" to 1,
        "Marrón" to 10,
        "Rojo" to 100,
        "Naranja" to 1000,
        "Amarillo" to 10000,
        "Verde" to 100000,
        "Azul" to 1000000,
        "Violeta" to 10000000,
        "Gris" to 100000000,
        "Blanco" to 1000000000,
        "Dorado" to 0.1,
        "Plateado" to 0.01
    )

    val colorTolerancia = mapOf(
        "Marrón" to 1,
        "Rojo" to 2,
        "Verde" to 0.5,
        "Azul" to 0.25,
        "Violeta" to 0.1,
        "Gris" to 0.05,
        "Dorado" to 5,
        "Plateado" to 10
    )

    val valor1 = colorValor[banda1] ?: 0
    val valor2 = colorValor[banda2] ?: 0
    val valorResistencia = (valor1 * 10 + valor2)
    val multiplicador = colorMultiplicador[banda3] ?: 1
    val resultado = valorResistencia * multiplicador.toDouble()
    val valorTolerancia = colorTolerancia[tolerancia]

    return if (valorTolerancia != null) {
        "$resultado Ω con una tolerancia de ±$valorTolerancia%"
    } else {
        "$resultado Ω sin tolerancia especificada"
    }
}