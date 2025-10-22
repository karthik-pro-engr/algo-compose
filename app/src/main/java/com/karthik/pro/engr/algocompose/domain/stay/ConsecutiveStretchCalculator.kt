package com.karthik.pro.engr.algocompose.domain.stay

object ConsecutiveStretchCalculator {
    fun computeResult(maxCapacity: Int, inputList: List<Int>): ConsecutiveSubArrayAndSize {
        var prefixSum = 0
        var left = 0
        var maxNights = 0
        var startIndex = -1
        var endIndex = -1
        for (i in 0 until inputList.size) {
            val current = inputList[i]
            prefixSum += current
            while (prefixSum > maxCapacity) {
                prefixSum = prefixSum - inputList[left]
                left++
            }
            val len = i - left + 1
            if (len > maxNights) {
                maxNights = len
                startIndex = left
                endIndex = i
            }
        }
        return if (maxNights == 0) ConsecutiveSubArrayAndSize(0,
            -1, -1)
        else ConsecutiveSubArrayAndSize(maxNights,
            startIndex,
            endIndex
        )
    }
}

data class ConsecutiveSubArrayAndSize(val maxStretch: Int, val startIndex: Int, val endIndex: Int)
