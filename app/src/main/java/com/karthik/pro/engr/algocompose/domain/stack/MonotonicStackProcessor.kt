package com.karthik.pro.engr.algocompose.domain.stack

object MonotonicStackProcessor {
    fun computeNextGreaterElement(inputList: List<Int>): NgeResult? {
        val size = inputList.size
        val output: MutableList<Int> = MutableList(size) { -1 }
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
        return NgeResult(output)
    }
}

data class NgeResult(val resultList: List<Int> = emptyList())