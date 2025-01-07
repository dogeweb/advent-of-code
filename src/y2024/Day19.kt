package y2024

import kotlin.invoke
import kotlin.time.measureTimedValue

fun main() {

    fun part1(input: List<String>) = run {
        val towels = input.first().split(", ").toSet()
        val maxLength = towels.maxOf { it.length }
        val map = mutableMapOf<String, Boolean>()

        val rec = DeepRecursiveFunction { s: String ->
            s.isEmpty() || map.getOrPut(s) {
                (1 .. s.length.coerceAtMost(maxLength))
                    .filter { s.take(it) in towels }
                    .any { callRecursive(s.drop(it)) }
            }
        }

        input.subList(2, input.size).count { rec(it) }
    }

    fun part2(input: List<String>) = run {
        val towels = input.first().split(", ").toSet()
        val maxLength = towels.maxOf { it.length }
        val map = mutableMapOf<String, Long>()

        val rec = DeepRecursiveFunction { s: String ->
            if(s.isEmpty()) 1L
            else map.getOrPut(s) {
                (1 .. s.length.coerceAtMost(maxLength))
                    .filter { s.take(it) in towels }
                    .sumOf { callRecursive(s.drop(it)) }
            }
        }

        input.subList(2, input.size).sumOf { rec(it) }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day19_test")
    check(part1(testInput).apply { println() } == 6)
    check(part2(testInput).apply { println() } == 16L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day19")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
