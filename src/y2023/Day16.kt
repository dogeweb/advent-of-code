package y2023

import java.util.*
import kotlin.time.measureTimedValue

fun main() {

    fun solve(input: List<String>, start: Triple<Int, Int, Int>): Int {

        val bounds = input[0].indices to input.indices
        val visited = mutableSetOf<Triple<Int, Int, Int>>()
        val queue = ArrayDeque<Triple<Int, Int, Int>>().apply { add(start) }

        while (queue.isNotEmpty()) {
            val (x, y, d) = queue.pollLast()

            val (nx, ny) = when (d) {
                0 -> x to y - 1
                1 -> x + 1 to y
                2 -> x to y + 1
                3 -> x - 1 to y
                else -> error("unreachable")
            }

            if (nx !in bounds.first || ny !in bounds.second
                || !visited.add(Triple(nx, ny, d))) continue

            when (input[ny][nx]) {
                '|' -> when (d) {
                    1 -> listOf(0, 2)
                    3 -> listOf(0, 2)
                    else -> listOf(d)
                }
                '-' -> when (d) {
                    0 -> listOf(1, 3)
                    2 -> listOf(1, 3)
                    else -> listOf(d)
                }
                '\\' -> when (d) {
                    0 -> 3
                    1 -> 2
                    2 -> 1
                    3 -> 0
                    else -> error("unreachable")
                }.let(::listOf)
                '/' -> when (d) {
                    0 -> 1
                    1 -> 0
                    2 -> 3
                    3 -> 2
                    else -> error("unreachable")
                }.let(::listOf)
                else -> listOf(d)
            }
            .map { Triple(nx, ny, it) }
            .let(queue::addAll)
        }

        return visited.distinctBy { it.first to it.second }.count()
    }

    fun part1(input: List<String>) = solve(input,Triple(-1, 0, 1))

    fun part2(input: List<String>) = sequenceOf(
        (-1..input[0].lastIndex + 1)
            .flatMap { listOf(Triple(it, -1, 2), Triple(it, input.size,0)) },
        (-1..input.lastIndex + 1)
            .flatMap { listOf(Triple(-1,  it, 1), Triple(input[0].length, it, 3)) }
    ).flatten().maxOf { solve(input, it) }

    val testInput = readInput("Day16_test")
    check(part1(testInput).apply { println() } == 46)
    check(part2(testInput).apply { println() } == 51)

    val input = readInput("Day16")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
