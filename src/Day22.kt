import java.io.DataInput
import kotlin.math.abs
import kotlin.time.measureTimedValue

fun main() {

    fun generatePrices(seed: Long) =
        generateSequence(seed) {
            var num = it
            num = (num * 64) xor num
            num %= 16777216
            num = (num / 32) xor num
            num %= 16777216
            num = (num * 2048) xor num
            num %= 16777216
            num
        }.take(2001)

    fun part1(input: List<String>) = input.sumOf { generatePrices(it.toLong()).last() }

    fun part2(input: List<String>) = input.map { it.toLong() }
        .flatMap {
            generatePrices(it)
                .map { it.toString().last().digitToInt() }
                .windowed(2)
                .map { it[0] - it[1] to it[1] }
                .windowed(4)
                .distinctBy { it.map { it.first } }
                .map { it.map { it.first } to it[3].second }
        }
        .groupBy { it.first }
        .maxOf { it.value.sumOf { it.second } }


//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day22_test")
    val testInput2 = readInput("Day22_test2")
    check(part1(testInput).apply { println() } == 37327623L)
    check(part2(testInput2).apply { println() } == 23)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day22")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
