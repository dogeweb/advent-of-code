package y2022

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .fold(mutableListOf(0)) { acc, s -> if (s.isBlank()) acc.add(0) else acc[acc.size - 1] = acc.last() + s.toInt(); acc }
            .max()
    }

    fun part2(input: List<String>): Int {
        return input
            .fold(mutableListOf(0)) { acc, s -> if (s.isBlank()) acc.add(0) else acc[acc.size - 1] = acc.last() + s.toInt(); acc }
            .sortedDescending()
            .take(3)
            .sum()
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    part1(testInput).println()
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
