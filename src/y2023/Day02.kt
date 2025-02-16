package y2023

import kotlin.math.max
import kotlin.time.measureTimedValue

fun main() {
    fun part1(input: List<String>): Int {

        return input.mapIndexed { index, it ->
            it.substringAfter(": ")
                .split("; ")
                .all {
                    it.split(", ").all {
                            val (num, col) = it.split(" ")
                            num.toInt() <= when(col) {
                                "red"   -> 12
                                "green" -> 13
                                "blue"  -> 14
                                else -> error("Invalid color $col")
                            }
                        }
            }.let { if (it) index + 1 else 0 }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.mapIndexed { index, it ->
            it.substringAfter(": ")
            .split("; ")
            .fold(arrayOf(0,0,0)) { acc, it ->
                it.split(", ").forEach {
                    val (num, col) = it.split(" ")
                    when(col) {
                        "red"   -> 0
                        "green" -> 1
                        "blue"  -> 2
                        else -> error("Invalid color $col")
                    }.let { acc[it] = max(acc[it], num.toInt()) }
                }
                acc
            }.reduce(Int::times)
        }.sum()
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput).apply { println() } == 8)
    check(part2(testInput).apply { println() } == 2286)

    val input = readInput("Day02")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
