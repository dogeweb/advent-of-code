
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

    fun part1(input: List<String>, threshold: Int): Int {

        var start = Pair(0, 0)
        var end = Pair(0, 0)
        val bounds = input.lastIndex - 2 to input[0].lastIndex - 2

        val obs = buildSet {
            input.drop(1).dropLast(1)
                .forEachIndexed { y, c ->
                    c.drop(1).dropLast(1).forEachIndexed { x, d ->
                        when (d) {
                            '#' -> add(Pair(x, y))
                            'S' -> start = Pair(x, y)
                            'E' -> end = Pair(x, y)
                        }
                    }
                }
        }

        val dist = calculateDistance(obs, start, end, bounds)[end]!!
        return obs.count { dist - calculateDistance(obs - it, start, end, bounds)[end]!! >= threshold }
    }

    fun part2(input: List<String>, cheatLength: Int, threshold: Int): Int {

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

        return points
            .flatMap { a -> points.map { b -> a to b } }
            .filter { (a, b) -> dist[a]!! < dist[b]!! }
            .filter { (a, b) -> abs(a.first - b.first) + abs(a.second - b.second) <= cheatLength }
            .count { (a, b) -> dist[b]!! - dist[a]!! - abs(a.first - b.first) - abs(a.second - b.second) >= threshold }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day20_test")
    check(part1(testInput, 1).apply { println() } == 44)

    check(part2(testInput, 20, 50).apply { println() } == 285)


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day20")
    measureTimedValue { part1(input, 100) }.println()
    measureTimedValue { part2(input, 20, 100) }.println()
}
