package com.example.playground

import java.util.Stack

class ExpressionCalculator {
    fun getPostFixExpressionOperation(originalExpression: String): Int {
        // 연산자들을 저장
        val stack = Stack<String>()
        val arr = strToArr(originalExpression)
        var result = ""

        for (e in arr) {
            when (e) {
                "+", "-", "*", "/" -> {
                    // peek : 가장 최근에 들어온 요소 반환
                    // pop: 가장 최근에 들어온 요소 "제거" 및 반환
                    while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(e)) {
                        // 스택에 연산자가 존재하고,
                        // 스택 최상단의 연산자가 현재 연산자보다 우선 순위가 높다면,
                        // result에 추가
                        result += stack.pop() + " "
                    }
                    stack.push(e)
                }

                "(" -> {
                    stack.push(e)
                }

                ")" -> {
                    while (stack.peek() != "(") {
                        result += stack.pop() + " "
                    }
                    stack.pop() // "(" 제거
                }

                else -> {
                    result += "$e "
                }
            }
        }

        while (!stack.isEmpty()) {
            result += stack.pop() + " "
        }

        println("---최종---")
        println("후위표기법: $result")

        val realResult = finalCalc(result)
        println("결과: $realResult")
        return realResult
    }

    // 변환된 수식을 가지고 실제로 계산을 수행
    fun finalCalc(result: String): Int {
        // 계산 예정인 숫자들을 저장
        val stack = Stack<String>()
        val calResult = result.split(" ")
        var result = 0

        for (e in calResult) {
            when (e) {
                "+" -> {
                    result = stack.pop().toInt() + stack.pop().toInt()
                    stack.push(result.toString())
                }

                "-" -> {
                    // Stack은 LIFO(선입후출) 구조
                    // (먼저 들어온 숫자 - 최근에 들어온 숫자)를 위함
                    result = -stack.pop().toInt() + stack.pop().toInt()
                    stack.push(result.toString())
                }

                "*" -> {
                    result = stack.pop().toInt() * stack.pop().toInt()
                    stack.push(result.toString())
                }

                "/" -> {
                    val num2 = stack.pop().toInt()
                    val num1 = stack.pop().toInt()
                    result = num1 / num2
                    stack.push(result.toString())
                }

                else -> {
                    stack.push(e)
                }
            }
        }
        return result
    }

    fun strToArr(str: String): Array<String> {
        // 괄호 뒤에 공백 추가
        var tempStr = str.replace("(", "( ")
        tempStr = tempStr.replace(")", " )")

        // 공백을 기준으로 나눠 배열 생성
        return tempStr.split(" ").toTypedArray()
    }

    // 연산자 우선 순위 계산
    fun precedence(operator: String): Int {
        return when (operator) {
            "+", "-" -> 1
            "*", "/" -> 2
            else -> 0
        }
    }
}