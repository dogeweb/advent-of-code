package y2024

import kotlin.time.measureTimedValue

fun main() {

    fun parseInput(input: List<String>) = input
        .filter { it.isNotBlank() }
        .chunked(7)
        .partition { it[0] == "#####" }
        .let { (locks, keys) ->
            val count = { p: List<String> -> p[0].indices.map { i -> p.count { it[i] == '#' } - 1 } }
            keys.map(count) to locks.map(count)
        }

    fun part1(input: List<String>) =
        parseInput(input).let { (keys, locks) ->
            keys.sumOf { k -> locks.count { l -> l.zip(k).all { (lock, key) -> lock + key < 6 } } }
        }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day25_test")
    check(part1(testInput).apply { println() } == 3)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day25")
    measureTimedValue { part1(input) }.println()
}
