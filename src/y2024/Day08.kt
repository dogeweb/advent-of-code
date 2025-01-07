package y2024

import kotlin.math.abs

fun main() {

    data class Point(val value: Char, val x: Int, val y: Int)

    fun parseInput(input: List<String>): Map<Char, List<Point>> {
        return input.mapIndexed { i, line ->
            "[a-zA-Z0-9]".toRegex().findAll(line).flatMap { it.groups }.mapNotNull { it }.map { Point(it.value[0], it.range.first, i) }
        }.flatMap { it }.groupBy { it.value }
    }

    tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

    fun part1(input: List<String>): Int {
        val set = mutableSetOf<Pair<Int, Int>>()
        parseInput(input).forEach { (_, v) ->
            v.forEachIndexed { index, a ->
                v.drop(index + 1).forEach { b ->
                    val vect = (b.x - a.x) to (b.y - a.y)
                    set.add(b.x + vect.first to b.y + vect.second)
                    set.add(a.x - vect.first to a.y - vect.second)
                }
            }
        }
        return set.count { it.first in input.indices && it.second in input[0].indices }
    }

    fun part2(input: List<String>): Int {

        val bounds = input.indices to input[0].indices
        val set = mutableSetOf<Pair<Int, Int>>()

        parseInput(input).forEach { (_, v) ->
            if (v.size == 1) return@forEach
            v.forEachIndexed { index, a ->
                v.drop(index + 1).forEach { b ->
                    var vect = (b.x - a.x) to (b.y - a.y)
                    val gcd = gcd(abs(vect.first), abs(vect.second))
                    vect = vect.first / gcd to vect.second / gcd
                    set.add(a.x to a.y)
                    var p = a.x to a.y
                    while (p.first in bounds.first && p.second in bounds.second) {
                        set.add(p)
                        p = p.first + vect.first to p.second + vect.second
                    }
                    p = a.x to a.y
                    while (p.first in bounds.first && p.second in bounds.second) {
                        set.add(p)
                        p = p.first - vect.first to p.second - vect.second
                    }
                }
            }
        }
        return set.count()
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput).also { println(it) } == 14)
    check(part2(testInput).also { println(it) } == 34)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
