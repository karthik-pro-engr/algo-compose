class Solution {
    fun minSubArrayLen(nums: List<String>): Triple<Int, Int, Int> {
        var left = 0
        var maxLen = 0
        var bestLeft = 0
        var bestRight = 0
        val map = HashMap<String, Int>()
        for (right in 0 until nums.size) {
            val current = nums[right]

            val prevIndex = map[current]
            if (prevIndex != null && prevIndex >= left) {
                left = prevIndex + 1
            }
            val len = right - left + 1
            if (len > maxLen) {
                maxLen = len
                bestLeft = left
                bestRight = right
            }


            map[current] = right

        }
        return Triple(maxLen, bestLeft,bestRight)
    }
}

fun main() {
    println(
        longestUniqueSegment(
            mutableListOf<String>(
                "rock",
                "pop",
                "jazz",
                "classical",
                "pop"
            )
        )
    )
    println(
        longestUniqueSegment(
            mutableListOf<String>(
                "pop",
                "pop",
                "pop",
                "pop",
                "pop"
            )
        )
    )
    println(
        longestUniqueSegment(
            mutableListOf<String>(
                "jazz",
                "pop",
                "pop",
                "pop",
                "pop"
            )
        )
    )
    println(
        longestUniqueSegment(
            mutableListOf<String>(
                "pop",
                "pop",
                "pop",
                "jazz",
                "rock"
            )
        )
    )

    println("*******************************************************")
    println(
        Solution().minSubArrayLen(
            mutableListOf<String>(
                "rock",
                "pop",
                "jazz",
                "classical",
                "pop"
            )
        )
    )
    println(
        Solution().minSubArrayLen(
            mutableListOf<String>(
                "pop",
                "pop",
                "pop",
                "pop",
                "pop"
            )
        )
    )
    println(
        Solution().minSubArrayLen(
            mutableListOf<String>(
                "jazz",
                "pop",
                "pop",
                "pop",
                "pop"
            )
        )
    )
    println(
        Solution().minSubArrayLen(
            mutableListOf<String>(
                "pop",
                "pop",
                "pop",
                "jazz",
                "rock"
            )
        )
    )
}


fun longestUniqueSegment(playlist: List<String>): Triple<Int, Int, Int> {
    // Returns Triple(length, startIndex, endIndex) using 0-based indices.
    val lastSeen = mutableMapOf<String, Int>() // song -> last index where it appeared
    var left = 0
    var bestLen = 0
    var bestLeft = 0
    var bestRight = 0

    for (right in playlist.indices) {
        val song = playlist[right]
        val prevIndex = lastSeen[song]
        if (prevIndex != null && prevIndex >= left) {
            // Duplicate found within current window [left..right-1].
            // Move left just past the previous occurrence to restore uniqueness.
            left = prevIndex + 1
        }
        // Record the last seen index for the current song
        lastSeen[song] = right

        val curLen = right - left + 1
        if (curLen > bestLen) {
            bestLen = curLen
            bestLeft = left
            bestRight = right
        }
        // Note: we update only when strictly greater; this enforces earliest start on ties.
    }

    return Triple(bestLen, bestLeft, bestRight)
}