package y2023

import kotlin.math.max
import kotlin.math.min
import kotlin.time.measureTimedValue

fun main() {

    fun List<String>.transpose(): List<String> =
        if (isEmpty()) emptyList() else first().indices.map { col -> joinToString("") { it[col].toString() } }

    fun parseInput(input: List<String>) =
        input.fold(mutableListOf(mutableListOf<String>())) { acc, item ->
            if (item.isEmpty()) acc.add(mutableListOf()) else acc.last().add(item)
            acc
        }

    fun List<String>.symmetry(check: Int? = null) =
        zipWithNext(String::equals)
            .withIndex()
            .filter { it.value }
            .map { it.index + 1 }
            .firstOrNull { index ->
                val range = max(0, index * 2 - size) until min(size, index * 2)
                (check?.let { it in range } ?: true) && slice(range).let { it == it.asReversed() }
            }

    fun part1(input: List<String>) =
        parseInput(input).sumOf { it.symmetry()?.times(100) ?: it.transpose().symmetry() ?: 0 }

    fun part2(input: List<String>) =
        parseInput(input)
            .sumOf { list ->
                val orig = list.symmetry()?.times(100) ?: list.transpose().symmetry()
                list.indices.firstNotNullOf { y ->
                    list[0].indices.firstNotNullOfOrNull { x ->
                        list.toMutableList()
                            .also { it[y] = list[y].toCharArray().also { it[x] = if(it[x] == '#') '.' else '#' }.concatToString() }
                            .run { listOf(symmetry(y)?.times(100), transpose().symmetry(x)) }
                            .filterNotNull().firstOrNull { it != orig }
                    }
                }
            }

    val testInput = readInput("Day13_test")
    check(part1(testInput).apply { println() } == 405)
    check(part2(testInput).apply { println() } == 400)

    val input = readInput("Day13")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
