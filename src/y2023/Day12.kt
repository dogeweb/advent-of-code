package y2023

import kotlin.time.measureTimedValue

fun main() {

    fun regex(int: Int) = "^\\.*[#?]{${int}}(?:[.?]|$)".toRegex()

    fun solve(input: List<String>, part2: Boolean = false): Long {

        val map = mutableMapOf<Pair<String, List<Int>>, Long>()
        val qmRegex = "^\\.*\\?".toRegex()


        val rec = DeepRecursiveFunction<Pair<String, List<Int>>, Long> { (s, nums) ->
            map.getOrPut(s to nums) {
                if (nums.isEmpty()) if ('#' in s) 0 else 1
                else (regex(nums[0]).find(s)?.let { callRecursive(s.drop(it.range.last + 1) to nums.drop(1)) }
                    ?: 0) + (qmRegex.find(s)?.let { callRecursive(s.drop(it.range.last + 1) to nums) } ?: 0)
            }
        }

        return if (!part2)
            input.sumOf { it.split(" ").let { (a, b) -> rec(a to b.split(",").map(String::toInt)) } }
        else
            input.sumOf {
                it.split(" ").let { (a, b) ->
                    rec(List(5) { a }.joinToString("?") to
                            b.split(",").map(String::toInt).let { r -> List(5) { r }.flatten() })
                }
            }
    }

    val testInput = readInput("Day12_test")
    check(solve(testInput).apply { println() } == 21L)
    check(solve(testInput, true).apply { println() } == 525152L)

    val input = readInput("Day12")
    measureTimedValue { solve(input) }.println()
    measureTimedValue { solve(input, true) }.println()
}
