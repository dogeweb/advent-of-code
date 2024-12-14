package y2023

import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {

        return input.mapIndexed { index, it ->
            it.substringAfter(":")
                .trim()
                .split(";")
                .all {
                    it.trim().split(",").all {
                            val (num, col) = it.trim().split(" ")
                            num.toInt() <= when(col) {
                                "red"   -> 12
                                "green" -> 13
                                "blue"  -> 14
                                else -> throw IllegalArgumentException("Invalid color $col")
                            }
                        }
            }.let { if (it) index + 1 else 0 }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.mapIndexed { index, it ->
            it.substringAfter(":")
            .trim()
            .split(";")
            .fold(arrayOf(0,0,0)) { acc, it ->
                it.trim().split(",").forEach {
                    val (num, col) = it.trim().split(" ")
                    when(col) {
                        "red"   -> 0
                        "green" -> 1
                        "blue"  -> 2
                        else -> throw IllegalArgumentException("Invalid color $col")
                    }.let { acc[it] = max(acc[it], num.toInt()) }
                }
                acc
            }.reduce(Int::times)
        }.sum()
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")

    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
