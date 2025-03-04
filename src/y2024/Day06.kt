package y2024

import kotlin.time.measureTimedValue

fun main() {

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second
    operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = first - other.first to second - other.second
    operator fun Pair<Int, Int>.div(div: Int) = first / div to second / div
    operator fun Pair<IntRange, IntRange>.contains(pair: Pair<Int, Int>) = pair.first in first && pair.second in second

    fun part1(input: List<String>): Int {
        val out = input.map { it.toCharArray() }.toTypedArray()
        var count = 1
        var direction = 0
        val bounds = input.indices to input[0].indices
        var pos = input.mapIndexed { i, j -> i to j.indexOfFirst { it == '^' } }.first { it.second != -1 }
        out[pos.first][pos.second] = 'X'
        while (pos.first in bounds.first && pos.second in bounds.second) {
            val next = when (direction % 4) {
                0 -> pos + (-1 to 0)
                1 -> pos + (0 to 1)
                2 -> pos + (1 to 0)
                else -> pos + (0 to -1)
            }
            if (next.first in bounds.first && next.second in bounds.second) {
                if (input[next.first][next.second] == '#') {
                    direction += 1
                } else {
                    pos = next
                    if (out[pos.first][pos.second] != 'X') {
                        out[pos.first][pos.second] = 'X'
                        count += 1
                    }
                }
            } else {
                break
            }
        }

//        y2024.println(out.joinToString("\n") { it.joinToString("") })
//        y2024.println()

        pos.println()
        return count
    }

    fun part2(input: List<String>): Int {
        val bounds = input.indices to input[0].indices

        val start = input
            .mapIndexed { i, j -> i to j.indexOfFirst { it == '^' } }
            .first { it.second != -1 }
            .let { Triple(it.first, it.second, 0) }

        fun next(pos: Triple<Int, Int, Int>, obst: Pair<Int, Int>): Triple<Int, Int, Int>? {
            val next = when (pos.third % 4) {
                0 -> -1 to 0
                1 -> 0 to +1
                2 -> +1 to 0
                else -> 0 to -1
            } + (pos.first to pos.second)

            if (next in bounds) {
                return if (next == obst || input[next.first][next.second] == '#')
                    next(Triple(pos.first, pos.second, (pos.third + 1) % 4), obst)
                else Triple(next.first, next.second, pos.third % 4)
            }
            return null
        }

        return input.indices.flatMap { i -> input[i].indices.map { j -> i to j } }
            .count { obst ->

                var t = start
                var h = start

                while (true) {
                    t = next(t, obst) ?: break
                    h = next(h, obst) ?: break
                    h = next(h, obst) ?: break
                    if (h == t) return@count true
                }
                false
            }

    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
//    part1(testInput).y2024.println()
    check(part1(testInput).also { println(it) } == 41)
    check(part2(testInput).also { println(it) } == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
