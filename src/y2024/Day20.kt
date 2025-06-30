package y2024
import kotlin.math.abs
import kotlin.math.min
import kotlin.time.TimeSource
import kotlin.time.TimeSource.Monotonic.ValueTimeMark
import kotlin.time.measureTimedValue

fun main() {

    val timeSource = TimeSource.Monotonic

    fun calculateDistance(
        nodes: Set<Pair<Int, Int>>,
        start: Pair<Int, Int>,
        bounds: Pair<IntRange, IntRange>,
    ): Map<Pair<Int, Int>, Int> {

        val visited = mutableSetOf(start)

        return generateSequence(start to 0) { (p, c) ->
            listOf(0 to 1, 1 to 0, -1 to 0, 0 to -1).map { p.first + it.first to p.second + it.second }
                .firstOrNull { z -> z.first in bounds.first && z.second in bounds.second && z !in nodes && z !in visited }
                ?.to(c + 1)
        }.onEach { visited.add(it.first) }.toMap()
    }

    fun solve(input: List<String>, cheatLength: Int, threshold: Int): Int {

        val marks = mutableListOf<ValueTimeMark>()
        marks.add(timeSource.markNow())

        var start = Pair(0, 0)
        val bounds = 0..input.lastIndex to 0..input[0].lastIndex

        val walls = buildSet {
            input.forEachIndexed { y, c ->
                c.forEachIndexed { x, d ->
                    when (d) {
                        '#' -> add(x to y)
                        'S' -> start = x to y
                    }
                }
            }
        }

        marks.add(timeSource.markNow())

        val points = calculateDistance(walls, start, bounds).entries.sortedBy { it.value }

        marks.add(timeSource.markNow())

        return points
            .dropLast(threshold)
            .asSequence()
            .flatMapIndexed { i, a ->
                points.subList(min(i + threshold + 1, points.size), points.size).asSequence().map(a::to)
            }
            .filter { (a, b) -> abs(a.key.first - b.key.first) + abs(a.key.second - b.key.second) <= cheatLength }
            .count { (a, b) -> b.value - a.value - abs(a.key.first - b.key.first) - abs(a.key.second - b.key.second) >= threshold }
            .also {
                marks.add(timeSource.markNow())
                marks.zipWithNext { a, b -> b - a }.println()
            }
    }

    val testInput = readInput("Day20_test")
    check(solve(testInput, 2, 1).apply { println() } == 44)
    check(solve(testInput, 20, 50).apply { println() } == 285)

    val input = readInput("Day20")
    measureTimedValue { solve(input, 2, 100) }.println()
    measureTimedValue { solve(input, 20, 100) }.println()
}
