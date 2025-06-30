package y2023

import kotlin.time.measureTimedValue

fun main() {

    fun parseLine(it: String) =
        it
        .split(" ")
        .map(String::toInt).let {
            generateSequence(it) { it.zipWithNext { a, b -> b - a } }
        }
        .takeWhile { it.sum() != 0 }

    fun part1(input: List<String>) =
        input.sumOf {
            parseLine(it)
            .sumOf { it.last() }
        }

    fun part2(input: List<String>) =
        input.sumOf {
            parseLine(it)
            .map { it.first() }
            .toList()
            .reduceRight(Int::minus)
        }

    val testInput = readInput("Day09_test")
    check(part1(testInput).apply { println() } == 114)
    check(part2(testInput).apply { println() } == 2)

    val input = readInput("Day09")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
