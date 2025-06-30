package y2024

import kotlin.time.measureTimedValue

fun main() {

    fun solve(input: List<String>, part2: Boolean = false) = run {
        val order = input.takeWhile { it.isNotBlank() }
            .mapTo(mutableSetOf()) { it.substring(0, 2) to it.substring(3, 5) }

        input
            .subList(order.size + 1, input.size).asSequence()
            .map { it.split(',') }
            .map { it to it.sortedWith { a, b -> if (a to b in order) -1 else if (b to a in order) 1 else 0 } }
            .filter { (original, sorted) -> (original == sorted) != part2 }
            .sumOf { (_, sorted) -> sorted[sorted.size / 2].toInt() }
    }

    fun part1(input: List<String>) = solve(input)

    fun part2(input: List<String>) = solve(input, true)

    val testInput = readInput("Day05_test")
    check(part1(testInput).apply { println() } == 143)
    check(part2(testInput).apply { println() } == 123)

    val input = readInput("Day05")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}