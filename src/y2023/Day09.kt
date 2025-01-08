package y2023

import kotlin.time.measureTimedValue

fun main() {

    fun part1(input: List<String>) =
        input.sumOf {
            generateSequence(it.split(" ").map(String::toInt)) { it.zipWithNext { a, b -> b - a } }
                    .takeWhile { it.sum() != 0 }
                    .sumOf { it.last() }
            }

    fun part2(input: List<String>) =
        input.sumOf {
                generateSequence(it.split(" ").map(String::toInt)) { it.zipWithNext { a, b -> b - a } }
                    .takeWhile { it.sum() != 0 }
                    .mapTo(mutableListOf()) { it.first() }
                    .reduceRight(Int::minus)
            }

    val testInput = readInput("Day09_test")
    check(part1(testInput).apply { println() } == 114)
    check(part2(testInput).apply { println() } == 2)

    val input = readInput("Day09")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
