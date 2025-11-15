package com.karthik.pro.engr.algocompose.domain

import com.karthik.pro.engr.algocompose.domain.energy.StretchResult

object PrefixSumWithMonotonic {
    fun findLongestFuelStretch(events: List<Long>): StretchResult {
        val n = events.size
        // Build prefix sums P[0..n]
        val P = LongArray(n + 1)
        for (i in 1..n) P[i] = P[i - 1] + events[i - 1]

        // nextLower[i] = first index j > i with P[j] < P[i], or n+1 if none
        val nextLower = IntArray(n + 1) { n + 1 }
        val stack = ArrayDeque<Int>() // will store indices with strictly increasing P

        for (j in 0..n) {
            while (stack.isNotEmpty() && P[j] < P[stack.last()]) {
                val i = stack.removeLast()
                nextLower[i] = j
            }
            stack.addLast(j)
        }

        // Remaining indices in stack have no smaller to the right; nextLower already n+1

        var maxLen = 0
        var bestStart = -1
        var bestEnd = -1

        // Consider starts i = 0..n-1 (which correspond to station S = i+1)
        for (i in 0 until n) {
            val len = nextLower[i] - i - 1
            if (len > maxLen) {
                maxLen = len
                bestStart = i + 1      // convert to 1-based station index
                bestEnd = nextLower[i] - 1
            }
            // Tie-breaking: we keep the earliest start because we only update when len > maxLen
        }

        if (maxLen == 0) return StretchResult(-1, -1, 0)
        return StretchResult(bestStart, bestEnd, maxLen)
    }

}

