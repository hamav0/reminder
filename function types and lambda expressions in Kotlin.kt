// -> (стрелка) разделяет список параметров и тело функции. Всё, что слева от стрелки — входящие данные, всё, что справа — выполняемый код.

fun main() {
 
/*
    val cupcake: (Int) -> String = { /* anyName */ ->  // cuz it is not used, you can skip it
        "Have a cupcake!"
    }
*/
    /*
    val coins: (Int) -> String = { // quantity -> // it can have any name
        "$it quarters" // we have only one parameter, you can use $it to also skip declaretion
    }
    but you can also skip declaring coins, kinda wierd but okay
      */ 
    
    //val treatFunction = trickOrTreat(false, { "$it quarters" })
    
    // but apperantly you can move lamda expretions outside of the function
    // it separates the lambda  from  parameters
    val treatFunction = trickOrTreat(false) { "$it quarters" }
    // in other words: If a function's last parameter is a function type, you can use trailing lambda syntax to move the lambda expression after the last parenthesis when you call a function.
    val trickFunction = trickOrTreat(true, null)
    
    repeat(4) {
        treatFunction()
    }
    trickFunction()

}
// ((Int) -> String)?) means it can be null
fun trickOrTreat(isTrick: Boolean, extraTreat: ((Int) -> String)?): () -> Unit {
    if (isTrick) {
        return trick
    } else {
        if (extraTreat != null) {
            println(extraTreat(5))
        }
        return treat
    }
}

val trick = {
    println("No treats!")
}

val treat = {
    println("Have a treat!")
}
