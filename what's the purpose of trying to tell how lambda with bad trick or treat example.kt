fun main() {
    printFinalTemperature(27.0, "Celsius", "Fahrenheit") { 9 / 5 * it + 32 }
    printFinalTemperature(350.0, "Kelvin", "Celsius") { it - 273.15 }
    printFinalTemperature(10.0, "Fahrenheit", "Kelvin") { 5/9 * (it - 32) + 273.15  }
}



fun printFinalTemperature(
    initialMeasurement: Double, // number1
    initialUnit: String, // number1 name
    finalUnit: String, // number2 name
    conversionFormula: (Double) -> Double // ur formula
) {
    val finalMeasurement = String.format("%.2f", conversionFormula(initialMeasurement)) // two decimal places
    println("$initialMeasurement degrees $initialUnit is $finalMeasurement degrees $finalUnit.")
}

// ai reminder
fun main() {
    // 1. Создаем лямбду с явным указанием имени параметра (initialTemp)
    val celsiusToFahrenheit = { initialTemp: Double -> 
        (9.0 / 5.0) * initialTemp + 32.0 
    }

    // 2. Передаем лямбду в качестве четвёртого аргумента внутри обычных скобок ()
    printFinalTemperature(
        27.0, 
        "Celsius", 
        "Fahrenheit", 
        celsiusToFahrenheit
    )
}

fun printFinalTemperature(
    initialMeasurement: Double,             // 27.0
    initialUnit: String,                    // "Celsius"
    finalUnit: String,                      // "Fahrenheit"
    conversionFormula: (Double) -> Double   // Наша функция-лямбда celsiusToFahrenheit
) {
    // 3. Вызываем лямбду conversionFormula, передав в неё initialMeasurement (27.0)
    val convertedValue = conversionFormula(initialMeasurement) 
    
    val finalMeasurement = String.format("%.2f", convertedValue)
    println("$initialMeasurement degrees $initialUnit is $finalMeasurement degrees $finalUnit.")
}
