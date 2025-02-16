package y2022

fun main() {
    fun part1(input: List<String>) = input.sumOf {
        when (it) {
            "A X" -> 3 + 1
            "A Y" -> 6 + 2
            "A Z" -> 0 + 3
            "B X" -> 0 + 1
            "B Y" -> 3 + 2
            "B Z" -> 6 + 3
            "C X" -> 6 + 1
            "C Y" -> 0 + 2
            "C Z" -> 3 + 3
            else -> error("Invalid input")
        }.toInt()
    }

    fun part2(input: List<String>) = input.sumOf {
        when (it) {
            "A X" -> 0 + 3
            "A Y" -> 3 + 1
            "A Z" -> 6 + 2
            "B X" -> 0 + 1
            "B Y" -> 3 + 2
            "B Z" -> 6 + 3
            "C X" -> 0 + 2
            "C Y" -> 3 + 3
            "C Z" -> 6 + 1
            else -> error("Invalid input")
        }.toInt()
    }

    val testInput = readInput("Day02_test")
    part1(testInput).println()
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
