package y2024

import kotlin.time.measureTimedValue

fun main() {

    val regex = "mul\\((\\d*),(\\d*)\\)".toRegex()

    fun part1(input: List<String>) =
        input.sumOf { regex.findAll(it).map { it.groupValues.drop(1).map(String::toInt).reduce(Int::times) }.sum() }

    fun part2(input: List<String>) =
        generateSequence(input.joinToString("")) { it.substringAfter("don't()", "").substringAfter("do()", "").takeIf { it.isNotBlank() } }
            .map { it.substringBefore("don't()") }
            .sumOf { regex.findAll(it).map { it.groupValues.drop(1).map { it.toInt() }.reduce(Int::times) }.sum() }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    val testInput2 = readInput("Day03_test2")
    check(part1(testInput) == 161)
    check(part2(testInput2) == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
