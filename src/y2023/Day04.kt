package y2023

import java.util.*
import kotlin.math.pow
import kotlin.time.measureTimedValue

fun main() {
    fun part1(input: List<String>) =
        input.mapIndexed { index, it ->
            val (win, my) = it.replaceBefore(": ", "")
                .split("|").map { "\\d+".toRegex().findAll(it).flatMap { it.groupValues }.toSet() }
            my.intersect(win).takeIf { it.isNotEmpty() }?.let { 2.0.pow(it.size - 1) } ?: 0.0
        }.sum().toInt()

    fun part2(input: List<String>) = run {
        val cards = input.mapIndexed { index, it ->
            val (win, my) = it.replaceBefore(": ", "")
                .split("|").map { "\\d+".toRegex().findAll(it).flatMap { it.groupValues }.toSet() }
            index + 1 to my.intersect(win).count()
        }
        val deque = ArrayDeque(cards)
        generateSequence(deque::pollFirst)
            .onEach { (i, n) -> cards.filter { it.first in i + 1..i + n }.let(deque::addAll) }
            .count()
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput).apply { println() } == 13)
    check(part2(testInput).apply { println() } == 30)

    val input = readInput("Day04")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
