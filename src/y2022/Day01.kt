package y2022

import kotlin.time.measureTimedValue

fun main() {

    fun parseInput(input: List<String>) =
        input.fold(mutableListOf(0)) { acc, s -> if (s.isBlank()) acc.add(0) else acc[acc.lastIndex] += s.toInt(); acc }


    fun part1(input: List<String>) = parseInput(input).max()

    fun part2(input: List<String>) =
        parseInput(input)
            .sortedDescending()
            .take(3)
            .sum()

    val testInput = readInput("Day01_test")
    part1(testInput).println()
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
