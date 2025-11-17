package com.karthik.pro.engr.algocompose.domain.vsw

import com.karthik.pro.engr.algocompose.domain.energy.StretchResult

object VswCalculation {
    fun <T> longestUniqueSegment(input: List<T>): StretchResult {
        val lastSeen = mutableMapOf<T, Int>() // song -> last index where it appeared
        var left = 0
        var bestLen = 0
        var bestLeft = 0
        var bestRight = 0

        for (right in input.indices) {
            val current = input[right]
            val prevIndex = lastSeen[current]
            if (prevIndex != null && prevIndex >= left) {
                left = prevIndex + 1
            }
            lastSeen[current] = right

            val curLen = right - left + 1
            if (curLen > bestLen) {
                bestLen = curLen
                bestLeft = left
                bestRight = right
            }

        }

        return StretchResult(bestLeft, bestRight, bestLen)
    }
}