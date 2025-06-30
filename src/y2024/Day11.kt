package y2024

import kotlin.time.measureTimedValue

fun main() {

    fun next(n: String) =
        when {
            n == "0" -> listOf("1")
            n.length % 2 == 0 -> listOf(
                n.take(n.length / 2),
                n.drop(n.length / 2).trimStart('0').ifEmpty { "0" })
            else -> listOf((n.toLong() * 2024).toString())
        }

    fun mapSolution(input: String, times: Int) = run {
        print("map = ")

        input.split(" ")
            .associateWith { 1L }
            .let {
                generateSequence(it) {
                    it.asSequence()
                        .flatMap { (n, v) -> next(n).map { it to v } }
                        .groupingBy { it.first }
                        .fold(0) { a, e -> a + e.second }
                }.elementAt(times).values.sum()
            }

    }

    fun dfsSolution(input: String, times: Int) = run {
        print("dfs = ")

        val map = mutableMapOf<Pair<String, Int>, Long>()

        val rec = DeepRecursiveFunction<Pair<String, Int>, Long> { (n, depth) ->
            if (depth == 0) 1
            else map.getOrPut(n to depth) { next(n).sumOf { callRecursive(it to depth - 1) } }
        }

        input.split(" ").sumOf { rec(it to times) }
    }

    val testInput = readInput("Day11_test").first()
    check(mapSolution(testInput, 6).apply { println() } == 22L)
    check(dfsSolution(testInput, 6).apply { println() } == 22L)
    check(mapSolution(testInput, 25).apply { println() } == 55312L)
    check(dfsSolution(testInput, 25).apply { println() } == 55312L)

    val input = readInput("Day11").first()
    measureTimedValue { mapSolution(input, 25) }.println()
    measureTimedValue { dfsSolution(input, 25) }.println()
    measureTimedValue { mapSolution(input, 75) }.println()
    measureTimedValue { dfsSolution(input, 75) }.println()
}