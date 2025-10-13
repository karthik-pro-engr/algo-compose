package com.karthik.pro.engr.algocompose.domain.stay

object BudgetStayCalculator {
    fun computeBudgetStay(budget: Int, hotelStayCostList: IntArray): ConsecutiveBudgetStay {
        var prefixSum = 0
        var left = 0
        var maxNights = 0
        var startIndex = -1
        var endIndex = -1
        for (i in 0 until hotelStayCostList.size) {
            val current = hotelStayCostList[i]
            prefixSum += current
            while (prefixSum > budget) {
                prefixSum = prefixSum - hotelStayCostList[left]
                left++
            }
            val len = i - left + 1
            if (len > maxNights) {
                maxNights = len
                startIndex = left
                endIndex = i
            }
        }
        return if (maxNights == 0) ConsecutiveBudgetStay(0,
            -1, -1)
        else ConsecutiveBudgetStay(maxNights,
            startIndex,
            endIndex
        )
    }
}

data class ConsecutiveBudgetStay(val maxNights: Int, val startIndex: Int, val endIndex: Int)

fun main() {
    val computeBudgetStay = BudgetStayCalculator.computeBudgetStay(
        50000,
        intArrayOf(50000, 1000, 20000, 20000, 9000, 55500, 60000)
    )
    println(
        "Nights-> ${computeBudgetStay.maxNights}, start-> ${computeBudgetStay.startIndex}, end-> ${computeBudgetStay.endIndex}"
    )
}