package chucknorris

fun main() {
    var inputOperator = ""
    do {
        println("Please input operation (encode/decode/exit):")
        inputOperator = readln()
        when (inputOperator) {
            "encode" -> {
                println("Input string:")
                val inputStringList = readln()
                println("Encoded string:")
                val binaryRes = convertToBinary(inputStringList)
                println(convertToChuckNorrisTechnique(binaryRes))
                println()
            }

            "decode" -> {
                println("Input encoded string:")
                val inputStringList = readln()
                val res = convertFromChuckNorrisTechnique(inputStringList)
                if (res == "Encoded string is not valid.") println("Encoded string is not valid.")
                else println("Decoded string:\n$res")
                println()
            }
            else -> {
                if (inputOperator != "exit") {
                    println("There is no '$inputOperator' operation")
                    println()
                }
            }

        }
    } while (inputOperator != "exit")
    println("Bye!")
}

fun convertToBinary(input: String): String {
    var convertibleString = ""
    for (i in input) {
        convertibleString += String.format("%07d", Integer.toBinaryString(i.code).toInt())
    }
    return convertibleString

}

fun convertToChuckNorrisTechnique(inputString: String): String {
    var chuckNorrisString = ""
    var i = 0
    var counter: Int
    while (i <= inputString.length-1) {
        if (inputString[i] == '1') {
            chuckNorrisString += "0 "
            counter = 1
            for (j in i..inputString.length-1) {
                if (j != inputString.length-1 && inputString[j] == inputString[j+1]) {
                    counter++
                    i++
                } else if (j == inputString.length-1 || inputString[j] != inputString[j+1]){
                    chuckNorrisString += "0".repeat(counter)
                    if (j != inputString.length-1) chuckNorrisString += " "
                    i++
                    break
                }
            }
        } else {
            chuckNorrisString += "00 "
            counter = 1
            for (j in i..inputString.length - 1) {
                if (j != inputString.length-1 && inputString[j] == inputString[j+1]) {
                    counter++
                    i++
                } else if (j == inputString.length-1 || inputString[j] != inputString[j+1]){
                    chuckNorrisString += "0".repeat(counter)
                    if (j != inputString.length-1) chuckNorrisString += " "
                    i++
                    break
                }
            }
        }
    }
    return chuckNorrisString
}

fun convertFromChuckNorrisTechnique(inputString: String): String {
    var restString = inputString

//    проверяем что в строчке только 0 и пробелы
    for (i in inputString) {
        if (i != '0' && i != ' ') {
            return "Encoded string is not valid."
        }
    }

//    проверяем что в строчке четкое колво блоков
    val countBlocks = inputString.split(' ').toList()
    if (countBlocks.size % 2 != 0) return "Encoded string is not valid."

// далее преобразуем в двоичную строку
    var stringResult = ""
    var stringResultFinal = ""
    while (restString.isNotEmpty()) {
        val stringNumber: String
        val oneOrNull = restString.substringBefore(" ")
        restString = restString.substringAfter(" ")
        if (oneOrNull.length == 1) {
            stringNumber = "1"
        } else if (oneOrNull.length == 2) {
            stringNumber = "0"
        } else {
            return "Encoded string is not valid."
        }
        val numberCount = restString.substringBefore(" ")
        restString = restString.substringAfter(" ")
        stringResult += stringNumber.repeat(numberCount.length)
        if (" " !in restString || restString == " ") break
    }
//  проверяем кратно ли 7ми получившаяся двоичная строка, если нет возвращает not valid
    if (stringResult.length % 7 != 0) return "Encoded string is not valid."

//    далее блоки по 7 двоичных символов конвертируем сначала в Int и затем в символ
    for (i in 0 .. stringResult.length-1 step 7) {
        val subStr = stringResult.substring(i,i+7)
        stringResultFinal += subStr.toInt(2).toChar()
    }
    return stringResultFinal
}