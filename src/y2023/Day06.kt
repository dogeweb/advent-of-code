package y2023

import kotlin.time.measureTimedValue

fun main() {

    fun part1(input: List<String>): Int {
        val regex = "[0-9]+".toRegex()
        val races = regex.findAll(input[0]).map { it.value.toInt() }
            .zip(regex.findAll(input[1]).map { it.value.toInt() })

        return races
            .map { z -> (0 .. z.first).map { it * (z.first - it) }.count { it > z.second } }
            .reduce(Int::times)
    }

    fun part2(input: List<String>): Int {
        val time = input[0].filter { it.isDigit() }.toLong()
        val record = input[1].filter { it.isDigit() }.toLong()
        return (0 .. time).map { it * (time - it) }.count { it > record }
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput).apply { println() } == 288)
    check(part2(testInput).apply { println() } == 71503)

    val input = readInput("Day06")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
