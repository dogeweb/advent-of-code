package y2024

import kotlin.time.measureTimedValue

fun main() {

    fun findSolution(a: Pair<Long, Long>, b: Pair<Long, Long>, t: Pair<Long, Long>): Pair<Double, Double>? {
        val det = a.first * b.second - a.second * b.first
        if (det == 0L) return null

        val x = (t.first * b.second - t.second * b.first) / det.toDouble()
        val y = (a.first * t.second - a.second * t.first) / det.toDouble()

        return if (x % 1 == 0.0 && y % 1 == 0.0) x to y else null
    }

    fun List<Triple<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>>.solveAll() =
        sumOf { findSolution(it.first, it.second, it.third)?.run { first * 3 + second } ?: 0.0 }.toLong()

    fun parseInput(input: List<String>, offset: Long = 0L) =
        "(\\d+)\\D+(\\d+)".toRegex().let { regex ->
            (input.indices step 4).map { index ->
                (0..2)
                    .asSequence()
                    .map(index::plus)
                    .map(input::get)
                    .mapNotNull(regex::find)
                    .map { it.groupValues.drop(1).map { it.toLong() }.let { (a, b) -> a to b } }
                    .toList()
                    .let { (a, b, c) -> Triple(a, b, c.first + offset to c.second + offset) }
            }
        }

    fun part1(input: List<String>) = parseInput(input).solveAll()

    fun part2(input: List<String>) = parseInput(input, 1E13.toLong()).solveAll()

    val testInput = readInput("Day13_test")
    measureTimedValue { part1(testInput).also { it == 480L } }.println()
    measureTimedValue { part2(testInput).also { it == 875318608908 } }.println()

    val input = readInput("Day13")
    measureTimedValue { part1(input).also { check(it == 38839L) } }.println()
    measureTimedValue { part2(input).also { check(it == 75200131617108) } }.println()
}
