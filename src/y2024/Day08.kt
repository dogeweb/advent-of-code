package y2024

import kotlin.time.measureTimedValue

fun main() {

    val regex = "[a-zA-Z0-9]".toRegex()

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second
    operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = first - other.first to second - other.second
    operator fun Pair<Int, Int>.div(div: Int) = first / div to second / div
    operator fun Pair<IntRange, IntRange>.contains(pair: Pair<Int, Int>) = pair.first in first && pair.second in second

    fun parseInput(input: List<String>) = input
        .flatMapIndexed { i, line ->
            regex
                .findAll(line)
                .flatMap { it.groups }
                .filterNotNull()
                .map { it.value.first() to (it.range.first to i) }
        }
        .groupBy({ it.first }, { it.second }).values

    tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

    fun part1(input: List<String>) =
        (input.indices to input[0].indices)
            .let { bounds ->
                parseInput(input)
                    .flatMapTo(mutableSetOf()) {
                        it.flatMapIndexed { index, a ->
                            it.subList(index + 1, it.size)
                                .flatMap { listOf(it + it - a, a + a - it) }
                        }
                    }
                    .count { it in bounds }
            }

    fun part2(input: List<String>) = (input.indices to input[0].indices)
        .let { bounds ->
            parseInput(input).flatMapTo(mutableSetOf()) {
                    it.flatMapIndexed { index, a ->
                        it.subList(index + 1, it.size)
                            .flatMap {
                                (it - a).run { div(gcd(first, second)) }
                                    .let { vect ->
                                        listOf(
                                            generateSequence(a, vect::plus),
                                            generateSequence(a) { it - vect }
                                        ).flatMap { it.takeWhile { it in bounds } }
                                }
                            }
                    }
                }.count()
        }

    val testInput = readInput("Day08_test")
    check(part1(testInput).also { println(it) } == 14)
    check(part2(testInput).also { println(it) } == 34)

    val input = readInput("Day08")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
