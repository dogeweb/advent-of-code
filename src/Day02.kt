import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.time.measureTimedValue

fun main() {

    fun parseInput(input: List<String>) = input
        .map { it.split(" ").map { it.toInt() } }

    fun isSafe(ints: List<Int>) =
        ints
        .zipWithNext { a, b -> (b - a) }
        .let {
            it.map { it.absoluteValue in 1..3 }.all { it }
                    && it.map { it.sign }.distinct().count() == 1
        }

    fun part1(input: List<String>) = parseInput(input).count(::isSafe)

    fun part2(input: List<String>) = parseInput(input)
        .count { l -> l.indices.any { l.toMutableList().apply { removeAt(it) }.let(::isSafe) } }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput).apply { println() } == 2)
    check(part2(testInput).apply { println() } == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
