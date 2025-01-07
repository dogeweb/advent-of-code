package y2024
import java.util.*
import kotlin.math.abs
import kotlin.time.measureTimedValue

fun main() {

    fun calculateDistance(
        nodes: Set<Pair<Int, Int>>,
        start: Pair<Int, Int>,
        end: Pair<Int, Int>,
        bounds: Pair<Int, Int>,
    ): Map<Pair<Int, Int>, Int> {

        val distances = mutableMapOf(start to 0)

        val priorityQueue = PriorityQueue(compareBy<Triple<Int, Int, Int>> { it.third })
            .apply { add(Triple(start.first, start.second, 0)) }

        generateSequence { priorityQueue.poll() }
            .takeWhile { it.first to it.second != end }
            .flatMap { (x, y, c) ->
                listOf(0 to 1, 1 to 0, -1 to 0, 0 to -1)
                    .map { Triple(x + it.first, y + it.second, c + 1) }
            }
            .filter { (x, y) -> x in 0..bounds.first && y in 0..bounds.second && x to y !in nodes }
            .forEach { (x, y, c) ->
                if (c < (distances[x to y] ?: Int.MAX_VALUE)) {
                    distances[x to y] = c
                    priorityQueue.add(Triple(x, y, c))
                }
            }

        return distances
    }

    fun solve(input: List<String>, cheatLength: Int, threshold: Int): Int {

        var start = Pair(0, 0)
        var end = Pair(0, 0)
        val bounds = input.lastIndex to input[0].lastIndex

        val walls = buildSet {
            input.forEachIndexed { y, c ->
                c.forEachIndexed { x, d ->
                    when (d) {
                        '#' -> add(Pair(x, y))
                        'S' -> start = Pair(x, y)
                        'E' -> end = Pair(x, y)
                    }
                }
            }
        }

        val dist = calculateDistance(walls, start, end, bounds)

        val points = input.indices
            .flatMap { y -> input[y].indices.map { x -> x to y } }
            .filter { it !in walls }
            .sortedBy { dist[it]!! }

        return points.indices
            .flatMap { a -> points.indices.drop(a).map { b -> a to b } }
            .asSequence()
            .map { (a, b) -> points[a] to points[b] }
            .filter { (a, b) -> dist[a]!! < dist[b]!! }
            .filter { (a, b) -> abs(a.first - b.first) + abs(a.second - b.second) <= cheatLength }
            .count { (a, b) -> dist[b]!! - dist[a]!! - abs(a.first - b.first) - abs(a.second - b.second) >= threshold }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day20_test")
    check(solve(testInput, 2, 1).apply { println() } == 44)
    check(solve(testInput, 20, 50).apply { println() } == 285)


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day20")
    measureTimedValue { solve(input, 2, 100) }.println()
    measureTimedValue { solve(input, 20, 100) }.println()
}
