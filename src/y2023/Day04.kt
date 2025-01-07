package y2023

import java.util.*
import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        return input.mapIndexed { index, it ->
            val (win, my) = it.replaceBefore(": ", "")
                .split("|").map { "\\d+".toRegex().findAll(it).flatMap { it.groupValues }.toSet() }
            my.intersect(win)
                .takeIf { it.isNotEmpty() }?.let { 2.0.pow(it.size - 1)} ?: 0.0
        }.sum().toInt()
    }

    fun part2(input: List<String>): Int {
        val cards = input.mapIndexed { index, it ->
            val (win, my) = it.replaceBefore(": ", "")
                .split("|").map { "\\d+".toRegex().findAll(it).flatMap { it.groupValues }.toSet() }
            index + 1 to my.intersect(win).count()
        }
        val deque = ArrayDeque<Pair<Int, Int>>()
        deque.addAll(cards)
        var count = 0
        while (deque.isNotEmpty()) {
            count++
            val (i, n) = deque.removeFirst()
            deque.addAll(cards.filter { it.first in i + 1..i + n })
        }

        return count
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
//    val testInput2 = y2024.readInput("Day04_test2")
    part1(testInput).println()
    part2(testInput).println()
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
