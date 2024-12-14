package y2022

import kotlin.jvm.Throws

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            when (it) {
                "A X" -> 3 + 1
                "A Y" -> 6 + 2
                "A Z" -> 0 + 3
                "B X" -> 0 + 1
                "B Y" -> 3 + 2
                "B Z" -> 6 + 3
                "C X" -> 6 + 1
                "C Y" -> 0 + 2
                "C Z" -> 3 + 3
                else -> throw IllegalArgumentException()
            }.toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            when (it) {
                "A X" -> 0 + 3
                "A Y" -> 3 + 1
                "A Z" -> 6 + 2
                "B X" -> 0 + 1
                "B Y" -> 3 + 2
                "B Z" -> 6 + 3
                "C X" -> 0 + 2
                "C Y" -> 3 + 3
                "C Z" -> 6 + 1
                else -> throw IllegalArgumentException()
            }.toInt()
        }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    part1(testInput).println()
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
