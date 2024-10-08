## Línea III Calculadora de Resitencias

## Definición
Herramienta para calcular los valores de las resistencias en (ohmios) a través del código de color,
cómo actividad del núcleo temático de línea de profundización III 901N.

## Descripción del código

El código se divide en varias funciones y composables, aprovechando el paradigma de Composición de Jetpack Compose:

    MainActivity: Esta es la actividad principal de la aplicación. En el método onCreate, se define el tema visual utilizando TemaResistenciaC y se carga el composable principal resistenciajeckpackcApp().

    resistenciajeckpackcApp: Este composable es la pantalla principal de la aplicación. En ella se definen las variables de estado para las bandas de colores (banda1, banda2, banda3) y la tolerancia. Se utilizan las funciones ColorDropdown para crear menús desplegables que permiten al usuario seleccionar los colores.
        ColorDropdown: Un composable que presenta un menú desplegable para seleccionar colores. Cada opción se muestra con un cuadro de color y su nombre. Esto facilita la visualización de los colores y su selección por parte del usuario.

    calcularResistencia: Esta función es responsable de calcular el valor de la resistencia en ohmios, basado en las bandas de colores seleccionadas. Se utilizan mapas (Map) para asociar los colores con sus valores numéricos y multiplicadores, lo que facilita el cálculo. Al final, se muestra el valor calculado con la tolerancia asociada.
        Los colores y sus valores numéricos se definen en los mapas colorValor, colorMultiplicador y colorTolerancia. Estos permiten convertir las selecciones de colores en valores numéricos para calcular la resistencia.

    Colores y Visualización: Se utiliza un mapa de colores (colorMap) para asociar cada nombre de color con su valor hexadecimal, lo que permite visualizar los cuadros de colores correspondientes dentro de la UI.

# Herramientas Utilizadas
* Android Studio IDE: Plataforma principal del desarrollo de la aplicación.
* Jetpack Compose: Framework de UI utilizado para las interfaces dinámicas y reactivas.
  Kotlin: Lenguaje de programación utilizado para la lógica de la aplicación.

## IA
Utilizada para resolver dudas referentes a la integración de los cuadritos de colores a las listas haciendo uso de valores hexadecimales, 
permitiendo facilitar y mejorar el desarrollo visual del proyecto.