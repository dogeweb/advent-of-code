package y2023

import kotlin.time.measureTimedValue

fun main() {

    fun solve(list: List<Pair<Int, Long>>) = list.asSequence().runningFold(0L to 0L) { (x, y), (c, steps) ->
            when (c) {
                0 -> x + steps to y
                1 -> x to y + steps
                2 -> x - steps to y
                3 -> x to y - steps
                else -> error("")
            }
        }.zipWithNext { (ax, ay), (bx, by) -> ax * by - bx * ay }.sum() / 2 + list.sumOf { it.second } / 2 + 1

    fun part1(input: List<String>) = input
        .map { it.split(" ") }
        .map { "RDLU".indexOf(it[0][0]) to it[1].toLong() }
        .let(::solve)

    fun part2(input: List<String>) = input
        .map { it.split(" ")[2].drop(2).dropLast(1) }
        .map { it[5].digitToInt() to it.take(5).toLong(16) }
        .let(::solve)

    val testInput = readInput("Day18_test")
    check(part1(testInput).apply { println() } == 62L)
    check(part2(testInput).apply { println() } == 952408144115)

    val input = readInput("Day18")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
