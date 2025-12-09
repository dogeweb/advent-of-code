package y2025

import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min
import kotlin.time.measureTimedValue

fun main() {

    fun parseInput(input: List<String>) = input
        .asSequence()
        .map { it[0] to it.substring(1).toInt() }
        .map { if (it.first == 'L') -it.second else it.second }
        .runningFold(50, Int::plus)

    fun part1(input: List<String>) =
        parseInput(input)
        .count { it % 100 == 0 }

    fun part2(input: List<String>) =
        parseInput(input)
        .windowed(2) { if (it[0] < it[1]) (it[0] + 1)..it[1] else it[1] until it[0]}
        .sumOf { it.count { it % 100 == 0 } }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")

    check(part1(testInput).apply { println() } == 3)
    check(part2(testInput).apply { println() } == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
