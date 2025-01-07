package y2024
import java.util.*
import kotlin.time.measureTimedValue

fun main() {

    fun parseInput(input: List<String>) =
        input.map { it.split(",").map { it.toInt() }.let { (a, b) -> a to b } }

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = this.first + other.first to this.second + other.second

    fun calculateDistance(nodes: Set<Pair<Int, Int>>, end: Pair<Int, Int>): Int? {

        val distances = mutableMapOf<Pair<Int, Int>, Int>()

        val priorityQueue = PriorityQueue(compareBy<Triple<Int, Int, Int>> { it.third })
            .apply { add(Triple(0, 0, 0)) }

        generateSequence { priorityQueue.poll() }
            .flatMap { (x, y, c) ->
                listOf(0 to 1, 1 to 0, -1 to 0, 0 to -1).map { Triple(x + it.first, y + it.second, c + 1) }
            }
            .filter { (x, y) -> x in 0..end.first && y in 0..end.second && (x to y) !in nodes }
            .forEach { (x, y, c) ->
                if (c < (distances[x to y] ?: Int.MAX_VALUE)) {
                    distances[x to y] = c
                    priorityQueue.add(Triple(x, y, c))
                }
            }

/*        (0..end.second).forEach { y ->
            (0..end.first).map { x ->
                when(x to y) {
                    in nodes -> '#'
                    in distances -> 'O'
                    else -> '.'
                }
            }.joinToString("").y2024.println()
        }*/

        return distances[end]
    }

    fun part1(input: List<String>, bytes: Int): Int {
        val nodes = parseInput(input)
            .take(bytes)
            .toSet()

        val end = nodes.maxOf { it.first } to nodes.maxOf { it.second }

        return calculateDistance(nodes, end) ?: -1
    }

    fun part2(input: List<String>): Pair<Int, Int> {
        val input = parseInput(input)
        val end = input.maxOf { it.first } to input.maxOf { it.second }
        return input.indices.toList()
            .binarySearch { calculateDistance(input.subList(0, it + 1).toSet(), end)?.unaryMinus() ?: 1 }
            .let { input[-it - 1] }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day18_test")
    check(part1(testInput, 12).apply { println() } == 22)
    check(part2(testInput).apply { println() } == 6 to 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day18")
    measureTimedValue { part1(input, 1024) }.println()
    measureTimedValue { part2(input) }.println()
}
