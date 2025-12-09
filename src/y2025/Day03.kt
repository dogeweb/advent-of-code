package y2025

import kotlin.time.measureTimedValue

fun main() {

    fun part1(input: List<String>) =
        input
            .sumOf {
                val a = it.dropLast(1).max()
                val b = it.substringAfter(a).max()
                "$a$b".toInt()
            }

    fun part2(input: List<String>) =
        input.sumOf {
            var t = it
            (11 downTo 0).map {
                val c = t.dropLast(it).max()
                t = t.substringAfter(c)
                c
            }.joinToString("").toLong()
        }

    val testInput = readInput("Day03_test")
    check(part1(testInput).apply { println() } == 357)
    check(part2(testInput).apply { println() } == 3121910778619L)

    val input = readInput("Day03")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
