package y2023

import kotlin.time.measureTimedValue

fun main() {
    fun part1(input: List<String>) =
        input.sumOf { it.filter { it.isDigit() }.run { (first() - '0') * 10 + (last() - '0') } }

    fun part2(input: List<String>): Int {
        val digits = listOf("one","two","three","four","five","six","seven","eight","nine").withIndex().associate { it.value to it.index + 1 }
        val regex = "(one|two|three|four|five|six|seven|eight|nine|[1-9])".toRegex()
        return input
            .sumOf {
                regex.findAll(it)
                    .run { arrayOf(first().value, last().value) }
                    .map { digits[it] ?: (it[0] - '0') }
                    .run { get(0) * 10 + get(1) }
            }
    }

    val testInput = readInput("Day01_test")
    val testInput2 = readInput("Day01_test2")
    check(part1(testInput).apply { println() } == 142)
    check(part2(testInput2).apply { println() } == 281)

    val input = readInput("Day01")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
