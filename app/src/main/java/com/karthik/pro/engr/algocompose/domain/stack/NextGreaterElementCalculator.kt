package com.karthik.pro.engr.algocompose.domain.stack

object NextGreaterElementCalculator {
    fun computeNextGreaterElement(inputList: List<Int>): IntArray {
        val size = inputList.size
        val output = IntArray(size) { -1 }
        val stack = ArrayDeque<Int>(size)

        for (i in size - 1 downTo 0) {
            val currentElement = inputList[i]
            while (stack.isNotEmpty() && inputList[stack.first()] <= currentElement) {
                stack.removeFirstOrNull()
            }
            if (stack.isNotEmpty()) {
                output[i] = stack.first()
            }
            stack.addFirst(i)
        }
        return output
    }
}

fun main() {
    println(
        NextGreaterElementCalculator.computeNextGreaterElement(listOf(20, 30, 10, 7, 3, 2, 25))
            .joinToString(", ")
    )
    println(
        NextGreaterElementCalculator.computeNextGreaterElement(
            listOf(
                20,
                30,
                10,
                7,
                3,
                2,
                2,
                3,
                25
            )
        ).joinToString(", ")
    )
}