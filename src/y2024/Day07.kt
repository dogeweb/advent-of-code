package y2024

import kotlin.time.measureTimedValue

fun main() {

    fun parseInput(input: List<String>) =
        input.asSequence()
            .map {
                val cal = it.substringBefore(":").toLong()
                val nums = it.substringAfter(":").trim().split(" ").map { it.toLong() }
                cal to nums
            }

    fun resolve(target: Long, ops:  List<(Long, Long) -> Long>, list: List<Long>): Boolean {
        if (list.size == 1) {
            return list[0] == target
        }
        return ops.any { resolve(target, ops, listOf(it(list[0], list[1])) + list.drop(2)) }
    }

    fun solve(input: List<String>, ops: List<(Long, Long) -> Long>): Long {
        return parseInput(input).sumOf { (cal, nums) ->
            if(resolve(cal, ops, nums)) cal else 0
        }
    }

    val ops = listOf<(Long, Long) -> Long>(Long::plus, Long::times, { a, b -> "$a$b".toLong() })

    val testInput = readInput("Day07_test")
    check(solve(testInput, ops.take(2)).also { println(it) } == 3749L)
    check(solve(testInput, ops).also { println(it) } == 11387L)

    val input = readInput("Day07")
    measureTimedValue { solve(input, ops.take(2)) }.println()
    measureTimedValue { solve(input, ops) }.println()
}
