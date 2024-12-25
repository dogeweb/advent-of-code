import kotlin.math.abs
import kotlin.math.absoluteValue

fun main() {

    fun parseInput(input: List<String>) = input
        .map { it.split("   ").map { it.toInt() }.let { (a, b) -> a to b } }
        .unzip()

    fun part1(input: List<String>): Int {
        return parseInput(input)
            .let {
                it.first.sorted()
                    .zip(it.second.sorted())
                    .sumOf { (first, second) -> (first - second).absoluteValue }
            }
    }

    fun part2(input: List<String>) =
        parseInput(input).let { (left, right) ->
            right.groupingBy { it }
                .eachCount()
                .let { rMap -> left.sumOf { l -> l * (rMap[l] ?: 0) } }

        }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
