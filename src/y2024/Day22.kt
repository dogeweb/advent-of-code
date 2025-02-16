package y2024

import kotlin.time.measureTimedValue

fun main() {

    fun generatePrices(seed: Long) =
        generateSequence(seed) {
            var num = (it shl 6 xor it) % 16777216
            num = (num shr 5 xor num) % 16777216
            num = (num shl 11 xor num) % 16777216
            num
        }.take(2001)

    fun part1(input: List<String>) = input.sumOf { generatePrices(it.toLong()).last() }

    fun part2(input: List<String>) = input.asSequence()
        .flatMap {
            generatePrices(it.toLong())
                .map { it.toInt() % 10 }
                .zipWithNext { a, b -> b - a to b }
                .windowed(4) { it.map { it.first } to it.last().second }
                .distinctBy { it.first }
        }
        .groupBy({ it.first }, { it.second })
        .maxOf { it.value.sum() }

    val testInput = readInput("Day22_test")
    val testInput2 = readInput("Day22_test2")
    check(part1(testInput).apply { println() } == 37327623L)
    check(part2(testInput2).apply { println() } == 23)

    val input = readInput("Day22")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
