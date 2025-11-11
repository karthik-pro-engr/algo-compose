package com.karthik.pro.engr.algocompose.domain.stack

object MonotonicStackProcessor {
    fun <T: Comparable<T>> computeNextGreaterElement(inputList: List<T>): NgeResult<T>? {
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

    fun <T: Comparable<T>> computePreviousGreaterElement(inputList: List<T>): NgeResult<T>? {
        val size = inputList.size
        val output: MutableList<Int> = MutableList(size) { -1 }
        val stack = ArrayDeque<Int>(size)

        for (i in 0 until  size) {
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

data class NgeResult<T>(val resultList: List<Int> = emptyList())