package y2024

import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.time.measureTimedValue

fun main() {

    fun parseInput(input: List<String>) =
        input.map { it.split(" ").map { it.toInt() } }

    fun isSafe(ints: List<Int>) =
        ints
            .zipWithNext(Int::minus)
            .run { all { it.absoluteValue in 1..3 } && distinctBy { it.sign }.count() == 1 }

    fun part1(input: List<String>) = parseInput(input).count(::isSafe)

    fun part2(input: List<String>) = parseInput(input)
        .count { l -> l.indices.any { l.toMutableList().apply { removeAt(it) }.let(::isSafe) } }

    val testInput = readInput("Day02_test")
    check(part1(testInput).apply { println() } == 2)
    check(part2(testInput).apply { println() } == 4)

    val input = readInput("Day02")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
