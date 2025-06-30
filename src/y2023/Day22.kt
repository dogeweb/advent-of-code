package y2023

import kotlin.time.measureTimedValue

fun main() {

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day22_test")
    check(part1(testInput).apply { println() } == 0)
    check(part2(testInput).apply { println() } == 0)

    val input = readInput("Day22")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
