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

    fun part2(input: List<String>) = input.map { it.toLong() }
        .flatMap {
            generatePrices(it)
                .map { it.toInt() % 10 }
                .zipWithNext { a, b -> b - a to b }
                .windowed(4) { it.map { it.first } to it.last().second }
                .distinctBy { it.first }
        }
        .groupBy { it.first }
        .maxOf { it.value.sumOf { it.second } }


//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day22_test")
    val testInput2 = readInput("Day22_test2")
    check(part1(testInput).apply { println() } == 37327623L)
    check(part2(testInput2).apply { println() } == 23)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day22")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
