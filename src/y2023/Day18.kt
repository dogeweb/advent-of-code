package y2023

import kotlin.time.measureTimedValue

fun main() {

    fun part1(input: List<String>): Int {
        return input
            .map { it.split(" ") }
            .map { it[0][0] to it[1].toInt() }
            .let {
                it.runningFold(0 to 0) { (x, y), (c, steps) ->
                    when (c) {
                        'U' -> x to y - steps
                        'D' -> x to y + steps
                        'L' -> x - steps to y
                        'R' -> x + steps to y
                        else -> error("")
                    }
                }.zipWithNext { (ax, ay), (bx, by) -> ax * by - bx * ay }.sum() / 2 + it.sumOf { it.second } / 2 + 1
            }
    }

    fun part2(input: List<String>): Long {
        return input
            .map { it.split(" ")[2].drop(2).dropLast(1) }
            .map { it.last().digitToInt() to it.take(5).toLong(16) }
            .let {
                it.runningFold(0L to 0L) { (x, y), (c, steps) ->
                    when (c) {
                        3 -> x to y - steps
                        1 -> x to y + steps
                        2 -> x - steps to y
                        0 -> x + steps to y
                        else -> error("")
                    }
                }.zipWithNext { (ax, ay), (bx, by) -> ax * by - bx * ay }.sum() / 2 + it.sumOf { it.second } / 2 + 1
            }
    }

    val testInput = readInput("Day18_test")
    check(part1(testInput).apply { println() } == 62)
    check(part2(testInput).apply { println() } == 952408144115)

    val input = readInput("Day18")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
