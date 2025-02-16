package y2024

import java.util.*
import kotlin.time.measureTimedValue

fun main() {

    fun parseInput(input: List<String>): Pair<Map<String, Int>, List<List<String>>> {
        val regex = "([a-z0-9]{3}) (AND|OR|XOR) ([a-z0-9]{3}) -> ([a-z0-9]{3})".toRegex()
        val map = input
            .takeWhile { it.isNotBlank() }.associate {
                it.substringBefore(":") to it.substringAfter(":").trim().toInt()
            }.toMutableMap()
        val queue = input.dropWhile { ":" in it || it.isBlank() }
            .map { regex.find(it)!!.groupValues.drop(1) }
        return map to queue
    }

    fun getNum(char: Char, values: Map<String, Int>): Long =
        values.filterKeys { it[0] == char }
            .entries
            .sortedByDescending { it.key }
            .joinToString("") { it.value.toString() }
            .toLong(2)

    fun solve(values: Map<String, Int>, operations: List<List<String>>): Long {
        val map = values.toMutableMap()
        val queue = LinkedList(operations)

        generateSequence { queue.poll() }.forEach { (a, op, b, c) ->
            if (map[a] == null || map[b] == null) queue.add(listOf(a, op, b, c))
            else
                map[c] = when(op) {
                    "AND" -> map[a]!! and map[b]!!
                    "OR" -> map[a]!! or map[b]!!
                    "XOR" -> map[a]!! xor map[b]!!
                    else -> error("Unknown operation")
                }
        }

        return getNum('z', map)
    }

    fun part1(input: List<String>): Long {

        val (map, operations) = parseInput(input)

        return solve(map, operations)
    }

    fun part2(input: List<String>): Int {
        return 0
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day24_test")
    val testInput2 = readInput("Day24_test2")
    check(part1(testInput).apply { println() } == 4L)
    check(part1(testInput2).apply { println() } == 2024L)
    check(part2(testInput2).apply { println() } == 0)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day24")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
