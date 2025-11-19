package com.karthik.pro.engr.algocompose.domain.energy

object EnergyAnalyzer {
    fun <T : Comparable<T>> findLongestStretch(
        houseTypes: List<T>,
        parser: (T) -> Int = { value -> value as Int }
    ): StretchResult {
        val result = IntArray(2)
        val map = HashMap<Int, Int>()
        map[0] = -1
        var prefixSum = 0
        var maxLen = 0
        houseTypes.forEachIndexed { index, element ->
            prefixSum += parser(element)
            if (map.containsKey(prefixSum)) {
                val len = index - map[prefixSum]!!
                if (len > maxLen) {
                    result[0] = map[prefixSum]!!
                    result[1] = index
                    maxLen = len
                }
            } else {
                map[prefixSum] = index
            }

            println(
                "index -> $index  prefixSum-> $prefixSum map-> ${
                    map.entries.joinToString(prefix = "{", postfix = "}") {
                        "[${it.key} -> ${it.value}]"
                    }
                } result-> ${result.joinToString(", ")}")
        }

        return StretchResult(result[0] + 1, result[1], maxLen)
    }
}

data class StretchResult(val startIndex: Int, val endIndex: Int, val length: Int)