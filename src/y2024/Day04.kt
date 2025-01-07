package y2024

import kotlin.time.measureTimedValue

fun main() {

    data class Point(val x: Int, val y: Int)

    fun part1(input: List<String>): Int {
        val x = input.flatMapIndexed { index, s ->
            "X".toRegex().findAll(s).flatMap { it.groups }.map { Point(it!!.range.first, index) }
        }

        val combs   = listOf(0 to 1, 0 to -1, 1 to 0, 1 to 1, 1 to -1, -1 to 0, -1 to 1, -1 to -1)
        val letters = listOf('M', 'A', 'S')

        return x.sumOf { xs ->
            combs.count { c ->
                runCatching {
                    letters
                        .withIndex()
                        .all { (i, q) -> input[xs.y + c.first * (i + 1)][xs.x + c.second * (i + 1)] == q }
                }.getOrDefault(false)
            }
        }
    }

    fun part2(input: List<String>): Int {
        val m = input.flatMapIndexed { index, s ->
            "M".toRegex().findAll(s).flatMap { it.groups }.map { Point(it!!.range.first, index) }
        }.toSet()
        val a = input.flatMapIndexed { index, s ->
            "A".toRegex().findAll(s).flatMap { it.groups }.map { Point(it!!.range.first, index) }
        }.toSet()
        val s = input.flatMapIndexed { index, s ->
            "S".toRegex().findAll(s).flatMap { it.groups }.map { Point(it!!.range.first, index) }
        }.toSet()

        return a.count { xs ->
            ((Point(xs.x - 1, xs.y - 1) in m && Point(xs.x + 1, xs.y + 1) in s)
                    || (Point(xs.x - 1, xs.y - 1) in s && Point(xs.x + 1, xs.y + 1) in m))
                    && ((Point(xs.x + 1, xs.y - 1) in m && Point(xs.x - 1, xs.y + 1) in s)
                    || (Point(xs.x + 1, xs.y - 1) in s && Point(xs.x - 1, xs.y + 1) in m))
        }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput).apply { println() } == 18)
    check(part2(testInput).apply { println() } == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
