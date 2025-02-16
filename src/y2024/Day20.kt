package y2024
import java.util.*
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
        end: Pair<Int, Int>,
        bounds: Pair<IntRange, IntRange>,
    ): Map<Pair<Int, Int>, Int> {

        val distances = mutableMapOf(start to 0)

        val priorityQueue = PriorityQueue(compareBy<Triple<Int, Int, Int>> { it.third })
            .apply { add(Triple(start.first, start.second, 0)) }

        generateSequence(priorityQueue::poll)
            .takeWhile { it.first to it.second != end }
            .flatMap { (x, y, c) ->
                listOf(0 to 1, 1 to 0, -1 to 0, 0 to -1)
                    .map { Triple(x + it.first, y + it.second, c + 1) }
            }
            .filter { (x, y) -> x in bounds.first && y in bounds.second && x to y !in nodes }
            .forEach { (x, y, c) ->
                if (c < (distances[x to y] ?: Int.MAX_VALUE)) {
                    distances[x to y] = c
                    priorityQueue.add(Triple(x, y, c))
                }
            }

        return distances
    }

    fun solve(input: List<String>, cheatLength: Int, threshold: Int): Int {

        val marks = mutableListOf<ValueTimeMark>()
        marks.add(timeSource.markNow())

        var start = Pair(0, 0)
        var end = Pair(0, 0)
        val bounds = 0..input.lastIndex to 0..input[0].lastIndex

        val walls = buildSet {
            input.forEachIndexed { y, c ->
                c.forEachIndexed { x, d ->
                    when (d) {
                        '#' -> add(x to y)
                        'S' -> start = x to y
                        'E' -> end = x to y
                    }
                }
            }
        }

        marks.add(timeSource.markNow())

        val points = calculateDistance(walls, start, end, bounds).entries.sortedBy { it.value }

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
