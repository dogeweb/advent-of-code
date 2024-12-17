import java.util.*
import kotlin.time.measureTimedValue

fun main() {

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = this.first + other.first to this.second + other.second

    data class IntQuadruple(val x: Int, val y: Int, val direction: Int, val cost: Int) {
        val triple = Triple(x, y, direction)
    }

    fun <A, B, C> Triple<A, B, C>.pair() = first to second

    fun findPaths(input: List<String>): Pair<MutableMap<Triple<Int, Int, Int>, Int>, MutableMap<Triple<Int, Int, Int>, Set<Triple<Int, Int, Int>>>> {

        val nextNodes =
            List(4) { mutableListOf(Triple(0, -1, 0), Triple(1, 0, 1), Triple(0, 1, 2), Triple(-1, 0, 3)) }
                .zip(listOf(2, 3, 0, 1))
                .map { it.first.apply { removeAt(it.second) }.toList() }

        val (xBounds, yBounds) = input[0].indices to input.indices

        val priorityQueue = PriorityQueue<IntQuadruple>(compareBy { it.cost }).apply {
            add(IntQuadruple(1, input.indices.last - 1, 0, 1000))
            add(IntQuadruple(1, input.indices.last - 1, 1, 0))
        }

        val distances = mutableMapOf<Triple<Int, Int, Int>, Int>().withDefault { Int.MAX_VALUE }
        val paths = mutableMapOf<Triple<Int, Int, Int>, Set<Triple<Int, Int, Int>>>().withDefault { emptySet() }

        while (priorityQueue.isNotEmpty()) {
            val (x, y, d, c) = priorityQueue.poll()
            nextNodes[d].asSequence()
                .map { IntQuadruple(it.first + x, it.second + y, it.third, c) }
                .filter { it.y in yBounds && it.x in xBounds }
                .filter { input[it.y][it.x] in ".E" }
                .forEach {
                    val totalDist = c + if (it.direction != d) 1001 else 1
                    val a = distances.getValue(it.triple)
                    when {
                        totalDist < a -> {
                            distances[it.triple] = totalDist
                            paths[it.triple] = setOf(Triple(x, y, d))
                            priorityQueue.add(it.copy(cost = totalDist))
                        }
                        totalDist == a -> paths[it.triple] = paths.getValue(it.triple) + Triple(x, y, d)
                    }
                }
        }

        return distances to paths
    }

    fun part1(input: List<String>) =
        findPaths(input).first.filter { it.key.first == input[0].length - 2 && it.key.second == 1 }.minOf { it.value }

    fun part2(input: List<String>): Int {

        val (distances, paths) = findPaths(input)

        val set = mutableSetOf<Pair<Int, Int>>()

        val rec = DeepRecursiveFunction<Triple<Int, Int, Int>, Unit> {
            set.add(it.pair())
            paths[it]?.forEach { callRecursive(it) }
        }

        distances
            .filter { it.key.first == input[0].length - 2 && it.key.second == 1 }
            .toList()
            .groupBy { it.second }
            .minBy { it.key }
            .value
            .forEach { rec(it.first) }

        return set.count()
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day16_test")
    check(part1(testInput).apply { println() } == 7036)
    check(part2(testInput).apply { println() } == 45)

    val testInput2 = readInput("Day16_test2")
    check(part1(testInput2).apply { println() } == 11048)
    check(part2(testInput2).apply { println() } == 64)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day16")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
