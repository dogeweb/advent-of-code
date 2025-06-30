package y2023

import java.util.*
import kotlin.math.abs
import kotlin.time.measureTimedValue

fun main() {

    fun solve(input: List<String>, steps: Int, part2: Boolean = false) = run {

        val parity = steps % 2
        val start = input[0].lastIndex / 2 to input.lastIndex / 2
        val heap = PriorityQueue<Triple<Int, Int, Int>>(compareBy { it.third })
        val distances = mutableMapOf<Pair<Int, Int>, Int>()
        distances[start] = 0

        heap.add(Triple(start.first, start.second, 0))

        generateSequence(heap::poll).forEach { (x, y, c) ->
            listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)
                .map { x + it.first to y + it.second }
                .filter(
                    if (part2)
                        { it -> input[abs(it.second % input.size)][abs(it.first % input[0].length)] != '#' }
                    else
                        { it -> it.first in input[0].indices && it.second in input.indices && input[it.second][it.first] != '#' }
                )
                .forEach {
                    val cost = c + 1
                    if (c < steps && cost < (distances[it] ?: Int.MAX_VALUE)) {
                        distances[it] = cost
                        heap.add(Triple(it.first, it.second, cost))
                    }
                }
        }

        distances.count { (point, _) -> (abs(start.first - point.first) + abs(start.second - point.second)) % 2 == parity }
    }

    fun lagrangeInterpolation(x: Double, vararg points: Pair<Double, Double>)= run {
        val (xs, ys) = points.unzip()
        xs.indices.sumOf { j ->
            ys[j] * (xs.indices - j).map { (x - xs[it]) / (xs[j] - xs[it]) }.reduce(Double::times)
        }
    }

    fun part1(input: List<String>) = solve(input, 64)

    fun part2(input: List<String>) = run {
        val (p0, p1, p2) = List(3) { 65 + 131 * it }
            .map { it.toDouble() to solve(input, it, true).toDouble() }

        lagrangeInterpolation(26501365.0, p0, p1, p2).toLong()
    }

    val testInput = readInput("Day21_test")
    check(measureTimedValue { solve(testInput, 6) }.apply { println() }.value == 16)

    val input = readInput("Day21")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
