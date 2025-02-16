package y2023

import kotlin.time.measureTimedValue

fun main() {

    fun hash(string: String) = string.toByteArray(Charsets.US_ASCII)
        .fold(0) { acc, b -> (acc + b) * 17 % 256 }

    fun part1(input: List<String>) = input.first().split(',').sumOf(::hash)

    val regex = "([a-z]+)([-=])(\\d)?".toRegex()

    fun part2(input: List<String>) = mutableMapOf<Int, MutableList<Pair<String, Int>>>()
        .also { map ->
            regex.findAll(input.first())
                .map(MatchResult::destructured)
                .forEach { (l, o, f) ->
                    val list = map.getOrPut(hash(l), ::mutableListOf)
                    val index = list.indexOfFirst { it.first == l }
                    when (o.first()) {
                        '=' -> if (index >= 0) list[index] = l to f.toInt() else list += l to f.toInt()
                        '-' if (index >= 0) -> list.removeAt(index)
                    }
                }
        }.entries.sumOf { (key, value) -> value.withIndex().sumOf { (key + 1) * (it.index + 1) * it.value.second } }

    check(hash("HASH").apply { println() } == 52)

    val testInput = readInput("Day15_test")
    check(part1(testInput).apply { println() } == 1320)
    check(part2(testInput).apply { println() } == 145)

    val input = readInput("Day15")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}