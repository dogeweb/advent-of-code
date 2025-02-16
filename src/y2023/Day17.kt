package y2023

import java.util.*
import kotlin.time.measureTimedValue

fun main() {

    data class State(val x: Int, val y: Int, val direction: Int, val steps: Int, val cost: Int)
    data class Quad(val x: Int, val y: Int, val direction: Int, val steps: Int)

    fun findMinimumCost(input: List<String>, isPart2: Boolean = false): Int {
        val dirs = listOf(0 to -1, 1 to 0, 0 to 1, -1 to 0)

        val heap = PriorityQueue<State>(compareBy { it.cost }).apply {
            add(State(0, 0, 1, 0, 0))
            add(State(0, 0, 2, 0, 0))
        }

        val distances = mutableMapOf<Quad, Int>().withDefault { Int.MAX_VALUE }
        val target = input[0].lastIndex to input.lastIndex

        while (heap.isNotEmpty()) {
            val q = heap.poll()
            if (q.x to q.y == target && (!isPart2 || q.steps > 3)) return q.cost

            when {
                isPart2 && q.steps in 0..3 -> listOf(q.direction)
                q.steps < 3 || (isPart2 && q.steps in 4..9) -> (0..3) - ((q.direction + 2) % 4)
                else -> (0..3) - ((q.direction + 2) % 4) - q.direction
            }.forEach { dir ->
                val (dx, dy) = dirs[dir]
                val nx = q.x + dx
                val ny = q.y + dy
                val steps = if (dir == q.direction) q.steps + 1 else 1
                if (nx in input[0].indices && ny in input.indices) {
                    val newState = State(nx, ny, dir, steps, q.cost + input[ny][nx].digitToInt())
                    val key = Quad(nx, ny, dir, steps)
                    if (newState.cost < distances.getValue(key)) {
                        distances[key] = newState.cost
                        heap.add(newState)
                    }
                }
            }
        }

        return Int.MAX_VALUE
    }

    fun part1(input: List<String>) = findMinimumCost(input)
    fun part2(input: List<String>) = findMinimumCost(input, isPart2 = true)

    val testInput = readInput("Day17_test")
    val testInput2 = readInput("Day17_test2")
    check(part1(testInput).also { println(it) } == 102)
    check(part2(testInput).also { println(it) } == 94)
    check(part2(testInput2).also { println(it) } == 71)

    val input = readInput("Day17")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
