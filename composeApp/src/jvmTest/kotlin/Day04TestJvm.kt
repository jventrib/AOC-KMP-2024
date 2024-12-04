import io.kotest.core.spec.style.FunSpec

class Day04TestJvm :
    FunSpec(
        {
          test("charDigit") {
            val X = 'X'.code
            val sum = "XMAS".sumOf { it.code }
            println(sum)
          }
        },
    )
