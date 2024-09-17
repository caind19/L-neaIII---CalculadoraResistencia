/*
    Aplicativo para calcular resintencia en ohmios por código de color.
    Cain David Martinez Cardona
    901N
*/
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

// Actividad principal de la aplicación que extiende ComponentActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplicar el tema personalizado al iniciar la interfaz de la aplicación
            TemaResistenciaC {
                resistenciajeckpackcApp() // Composable que maneja la interfaz principal de la aplicación
            }
        }
    }
}

// Función composable que representa la aplicación de cálculo de resistencias
@Composable
fun resistenciajeckpackcApp() {
    // Variables de estado que almacenan los valores seleccionados para cada banda de color
    var banda1 by remember { mutableStateOf("Marrón") } // Primera banda de color
    var banda2 by remember { mutableStateOf("Negro") }  // Segunda banda de color
    var banda3 by remember { mutableStateOf("Rojo") }   // Tercera banda (multiplicador)
    var tolerancia by remember { mutableStateOf("Dorado") } // Tolerancia de la resistencia
    var resultado by remember { mutableStateOf("") }    // Resultado del cálculo

    // Opciones de colores para las bandas
    val colorOptions = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
    // Opciones de tolerancia
    val toleranciaOptions = listOf("Dorado", "Plateado", "Marrón", "Rojo", "Verde", "Azul", "Violeta", "Gris")

    // Función que calcula la resistencia en base a los valores seleccionados
    fun calcular() {
        resultado = calcularResistencia(banda1, banda2, banda3, tolerancia)
    }

    // Definición de la interfaz de usuario
    Column(
        modifier = Modifier
            .fillMaxSize() // Toma todo el espacio disponible
            .padding(16.dp), // Añade margen de 16dp alrededor del contenido
        verticalArrangement = Arrangement.spacedBy(16.dp), // Espaciado entre elementos de la columna
        horizontalAlignment = Alignment.CenterHorizontally // Alineación horizontal al centro
    ) {
        // Título de la aplicación
        Text(
            text = "Calculadora de Resistencias",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary // Color de texto basado en el tema
        )

        // Composables que permiten seleccionar los colores de las bandas
        ColorDropdown("Banda 1", colorOptions, banda1) { newValue ->
            banda1 = newValue // Actualiza la banda 1
            calcular() // Recalcula el valor de la resistencia
        }
        ColorDropdown("Banda 2", colorOptions, banda2) { newValue ->
            banda2 = newValue // Actualiza la banda 2
            calcular()
        }
        ColorDropdown("Banda 3 (Multiplicador)", colorOptions, banda3) { newValue ->
            banda3 = newValue // Actualiza la banda 3 (multiplicador)
            calcular()
        }

        // Selección de la tolerancia
        ColorDropdown("Tolerancia", toleranciaOptions, tolerancia) { newValue ->
            tolerancia = newValue // Actualiza la tolerancia
            calcular()
        }

        Spacer(modifier = Modifier.height(16.dp)) // Añade un espacio entre las secciones

        // Mostrar el resultado del cálculo
        Text(
            text = "El Resultado es = $resultado", // Muestra el resultado calculado
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

// Mapa que asocia cada color con su respectivo valor hexadecimal
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

// Composable para mostrar un menú desplegable de selección de color
@Composable
fun ColorDropdown(label: String, items: List<String>, selectedItem: String, onItemSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) } // Estado que controla si el menú está expandido

    Box(modifier = Modifier.fillMaxWidth()) {
        // Campo de texto donde se muestra el valor seleccionado
        TextField(
            value = selectedItem,
            onValueChange = {}, // No cambia el valor directamente en el campo
            label = { Text(label) }, // Etiqueta del campo
            readOnly = true, // El campo es de solo lectura
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true } // Expande el menú cuando se hace clic
        )
        // Menú desplegable que contiene las opciones de color
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false } // Cierra el menú si se hace clic fuera de él
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            // Cuadro de color correspondiente a cada opción
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(colorMap[item] ?: Color.Transparent) // Asocia el color del mapa
                                    .padding(end = 8.dp)
                            )
                            Text(text = item) // Muestra el nombre del color
                        }
                    },
                    onClick = {
                        onItemSelected(item) // Actualiza el valor seleccionado cuando se elige un color
                        expanded = false // Cierra el menú
                    }
                )
            }
        }
    }
}

// Función que calcula el valor de la resistencia en base a los colores seleccionados
fun calcularResistencia(banda1: String, banda2: String, banda3: String, tolerancia: String?): String {
    // Mapa que asocia cada color con su valor correspondiente
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

    // Mapa que asocia cada color con su valor de multiplicador
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

    // Mapa que asocia cada color con su valor de tolerancia
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

    // Calcula el valor de la resistencia basado en las dos primeras bandas
    val valor1 = colorValor[banda1] ?: 0
    val valor2 = colorValor[banda2] ?: 0
    val valorResistencia = (valor1 * 10 + valor2) // Valor de las dos primeras bandas

    // Multiplica por el valor de la tercera banda (multiplicador)
    val multiplicador = colorMultiplicador[banda3] ?: 1
    val resultado = valorResistencia * multiplicador.toDouble() // Valor final de la resistencia

    // Obtiene el valor de la tolerancia
    val valorTolerancia = colorTolerancia[tolerancia]

    // Retorna el resultado en Ω y su tolerancia
    return if (valorTolerancia != null) {
        "$resultado Ω con una tolerancia de ±$valorTolerancia%" // Si la tolerancia es válida
    } else {
        "$resultado Ω sin tolerancia especificada" // Si no se especificó tolerancia
    }
}