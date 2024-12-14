import kotlin.math.abs
import kotlin.math.sign

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.split(" ").map { it.toInt() } }
            .count {
                it.windowed(2).all { abs(it.first() - it.last()) in 1..3 }
                        && it.windowed(2).map { (it.first() - it.last()).sign }.toSet().size == 1
            }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split(" ").map { it.toInt() } }
            .count { list ->
                list.indices
                    .map { list.toMutableList().apply { removeAt(it) } }
                    .any {
                        it.windowed(2).all { abs(it.first() - it.last()) in 1..3 }
                                && it.windowed(2).map { (it.first() - it.last()).sign }.toSet().size == 1
                    }
            }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
