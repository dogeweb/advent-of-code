package y2023

import kotlin.time.measureTimedValue

fun main() {

    fun parseInput(input: List<String>): Pair<Pair<Int, Int>, Map<Pair<Int, Int>, List<Pair<Int, Int>>>> {
        var start = 0 to 0
        val edges = buildMap {
            input.forEachIndexed { y, s ->
                s.forEachIndexed { x, p ->
                    when (p) {
                        '|' -> set(x to y, listOf(x to y - 1, x to y + 1))
                        '-' -> set(x to y, listOf(x - 1 to y, x + 1 to y))
                        'L' -> set(x to y, listOf(x to y - 1, x + 1 to y))
                        'J' -> set(x to y, listOf(x - 1 to y, x to y - 1))
                        '7' -> set(x to y, listOf(x - 1 to y, x to y + 1))
                        'F' -> set(x to y, listOf(x + 1 to y, x to y + 1))
                        '.' -> {}
                        'S' -> start = x to y
                    }
                }
            }
        }
        return start to edges
    }


    fun part1(input: List<String>): Int {
        val (start, edges) = parseInput(input)
        val a = edges.filter { start in it.value }.toList().first().first

        return generateSequence(start to a) { (prev, curr) ->
            edges[curr]?.minus(prev)?.single()
                ?.takeIf { it != start }
                ?.let { curr to it }
        }.count() / 2 + 1
    }

    fun part2(input: List<String>): Int {

        val (start, edges) = parseInput(input)
        val a = edges.filter { start in it.value }.toList().first().first

        val set = generateSequence(start to a) { (prev, curr) ->
            edges[curr]?.minus(prev)?.single()
                ?.takeIf { it != start }
                ?.let { curr to it }
        }.map { it.second }.toSet() + start

        val q = edges.filter { start in it.value }.keys.toList().let { (a, b) ->
            setOf(a.first - start.first to a.second - start.second, b.first - start.first to b.second - start.second)
        }.let {
            when(it) {
                setOf(0 to -1, 1 to 0) -> 'L'
                setOf(-1 to 0, 0 to 1) -> '7'
                setOf(-1 to 0, 0 to -1) -> 'J'
                setOf(0 to 1, 1 to 0) -> 'F'
                setOf(-1 to 0, 1 to 0) -> '-'
                setOf(0 to -1, 0 to 1) -> '|'
                else -> throw IllegalStateException()
            }
        }

        var count = 0

        input.forEachIndexed { y, s ->
            var last = '.'
            var inside = false
            s.forEachIndexed { x, p ->
                val z = if(x to y == start) q else p
                if (x to y in set) {
                    when (z) {
                        '|' -> { inside = !inside }
                        'L' -> { last = z }
                        'J' -> { if (last == 'F') inside = !inside; last = z }
                        '7' -> { if (last == 'L') inside = !inside; last = z }
                        'F' -> { last = z }
                    }
                } else if (inside) count++
            }
        }

        return count
    }

    val testInput = readInput("Day10_test")
    val testInput2 = readInput("Day10_test2")
    val testInput3 = readInput("Day10_test3")
    val testInput4 = readInput("Day10_test4")
    val testInput5 = readInput("Day10_test5")
    check(part1(testInput).apply { println() } == 4)
    check(part2(testInput2).apply { println() } == 4)
    check(part2(testInput3).apply { println() } == 4)
    check(part2(testInput4).apply { println() } == 8)
    check(part2(testInput5).apply { println() } == 10)

    val input = readInput("Day10")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
