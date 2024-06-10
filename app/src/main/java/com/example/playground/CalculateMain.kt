package com.example.playground

fun main() {
    val add = Calculator(AddOperation())
    val sub = Calculator(SubtractOperation())
    val mul = Calculator(MultiplyOperation())
    val div = Calculator(DivideOperation())

    while (true) {
        val (x, y) = getNumberList()
        val type = getType()

        when (type) {
            -1 -> {
                println("종료합니다.")
                break
            }

            1 -> println("덧셈 결과 >> ${add.calculate(x, y)}")
            2 -> println("뺄셈 결과 >> ${sub.calculate(x, y)}")
            3 -> println("곱셈 결과 >> ${mul.calculate(x, y)}")
            4 -> println("나눗셈 결과 >> ${div.calculate(x, y)}")
            else -> println("올바른 값을 입력해주세요.")
        }
    }

    val expression = "(5 + 2) * (4 / 2) + 1"
    ExpressionCalculator().getPostFixExpressionOperation(expression)
}

fun getNumberList(): List<Int> {
    println("계산하실 숫자를 공백으로 구분하여 입력해주세요.")

    while (true) {
        readlnOrNull()?.let {
            try {
                if (it.contains(" ")) return it.split(" ").map(String::toInt)
                else throw NumberFormatException()
            } catch (e: NumberFormatException) {
                println("올바른 값을 입력해주세요.")
            }
        }
    }
}

fun getType(): Int {
    println(
        """계산을 선택해주세요. 그만하려면 -1을 입력해주세요.
            |1. 더하기 2. 빼기 3. 곱하기 4. 나누기""".trimMargin()
    )

    while (true) {
        readlnOrNull()?.let {
            try {
                return it.toInt()
            } catch (e: NumberFormatException) {
                println("숫자를 입력해주세요.")
            }
        }
    }
}
