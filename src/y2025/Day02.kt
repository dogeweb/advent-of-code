package y2025

import kotlin.time.measureTimedValue

fun main() {

    fun parseInput(input: List<String>) =
        input[0].split(",")
        .map { it.split('-') }
        .map { it[0].toLong()..it[1].toLong() }

    fun part1(input: List<String>) =
        parseInput(input)
        .sumOf {
            it.filter {
                val s = it.toString()
                s.length % 2 == 0 && s.chunked(s.length / 2).toSet().size == 1
            }.sum()
        }

    fun part2(input: List<String>) =
        parseInput(input)
        .sumOf {
            it.filter {
                val s = it.toString()
                (1 until s.length).any { s.chunked(it).toSet().size == 1 }
            }.sum()
        }

    val testInput = readInput("Day02_test")
    check(part1(testInput).apply { println() } == 1227775554L)
    check(part2(testInput).apply { println() } == 4174379265)

    val input = readInput("Day02")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
