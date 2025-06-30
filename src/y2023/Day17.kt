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

        generateSequence(heap::poll).forEach {
            if (it.x to it.y == target && (!isPart2 || it.steps > 3)) return it.cost

            when {
                isPart2 && it.steps in 0..3 -> listOf(it.direction)
                it.steps < 3 || (isPart2 && it.steps in 4..9) -> (0..3) - ((it.direction + 2) % 4)
                else -> (0..3) - ((it.direction + 2) % 4) - it.direction
            }
            .forEach { dir ->
                val (dx, dy) = dirs[dir]
                val nx = it.x + dx
                val ny = it.y + dy
                val steps = if (dir == it.direction) it.steps + 1 else 1
                if (nx in input[0].indices && ny in input.indices) {
                    val newState = State(nx, ny, dir, steps, it.cost + input[ny][nx].digitToInt())
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
