package y2024

import kotlin.math.abs
import kotlin.time.measureTimedValue

fun main() {

    val pad = "789456123 0A ^K<v>"
        .mapIndexed { i, c -> c to ((i % 3) to i / 3) }
        .toMap()

    fun paths(start: Pair<Int, Int>, end: Pair<Int, Int>): Sequence<String> =
        if(start == end) sequenceOf("K")
        else sequence {
            if (start.first < end.first)
                paths(start.first + 1 to start.second, end).map { ">$it" }.let { yieldAll(it) }
            else if (start.first > end.first && start != (1 to 4) && start != (1 to 3))
                paths(start.first - 1 to start.second, end).map { "<$it" }.let { yieldAll(it) }
            if (start.second < end.second && start != (0 to 2))
                paths(start.first to start.second + 1, end).map { "v$it" }.let { yieldAll(it) }
            else if (start.second > end.second && start != (0 to 5))
                paths(start.first to start.second - 1, end).map { "^$it" }.let { yieldAll(it) }
        }

    fun manhattanDistanceAndPress(start: Pair<Int, Int>, end: Pair<Int, Int>) =
        abs(start.first - end.first) + abs(start.second - end.second) + 1L

    fun solve(input: List<String>, robotsCount: Int) = run {

        val pathsMap = with(mutableMapOf<Pair<Pair<Int, Int>, Pair<Int, Int>>, Sequence<String>>()) {
            withDefault { getOrPut(it) { paths(it.first, it.second) } }
        }

        val map = mutableMapOf<Pair<String, Int>, Long>()

        val rec = DeepRecursiveFunction<Pair<String, Int>, _> { (s, i) ->
            map.getOrPut(s to i) {
                "K$s".map(pad::getValue)
                    .run {
                        if (i == robotsCount) zipWithNext(::manhattanDistanceAndPress)
                        else zipWithNext { a, b -> pathsMap.getValue(a to b).minOf { callRecursive(it to i + 1) } }
                    }.sum()
                }
            }

        input.sumOf {
            "A$it".map(pad::getValue)
                .zipWithNext { a, b -> pathsMap.getValue(a to b) }
                .sumOf { it.minOf { rec(it to 1) } } * it.dropLast(1).toLong()
        }
    }

    val testInput = readInput("Day21_test")
    check(solve(testInput, 2).apply { println() } == 126384L)
    solve(testInput, 25)

    val input = readInput("Day21")
    measureTimedValue { solve(input, 2) }.println()
    measureTimedValue { solve(input, 25) }.println()
}
