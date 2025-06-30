package y2023

import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min
import kotlin.time.measureTimedValue

fun main() {

    fun solve(input: List<String>, mult: Long): Long {

        val vIndex = input.mapIndexedNotNull { index, it -> if ('#' !in it) index else null }

        val hIndex = (0..input[0].lastIndex)
            .map { index -> input.map { it.elementAt(index) }.joinToString("") }
            .mapIndexedNotNull { index, it -> if ('#' !in it) index else null }

        val list = input.flatMapIndexed { y, s ->
            s.mapIndexedNotNull { x, st -> if (st == '#') x to y else null }
        }

        return list.mapIndexed { index, it ->
            list.subList(index, list.size).sumOf { o ->
                val hRange = min(it.first, o.first)..max(it.first, o.first)
                val vRange = min(it.second, o.second)..max(it.second, o.second)
                val a = (it.first - o.first).absoluteValue + (it.second - o.second).absoluteValue
                val vert = vIndex.count { it in vRange } * (mult - 1)
                val horz = hIndex.count { it in hRange } * (mult - 1)
                a + vert + horz
            }
        }.sum()
    }

    val testInput = readInput("Day11_test")
    check(solve(testInput, 2L).apply { println() } == 374L)
    check(solve(testInput, 10L).apply { println() } == 1030L)
    check(solve(testInput, 100L).apply { println() } == 8410L)

    val input = readInput("Day11")
    measureTimedValue { solve(input, 2L) }.println()
    measureTimedValue { solve(input, 1000000) }.println()
}
