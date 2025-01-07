package y2023

import y2024.println

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .sumOf {
                it.filter { it.isDigit() }.let {
                    "${it.first()}${it.last()}"
                }.toInt()
            }
    }

    fun part2(input: List<String>): Int {
        val digits = mapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)
        return input
            .sumOf { string ->
                val first = string.indices
                    .map { string.substring(0..it) }
                    .firstNotNullOf { string ->
                        if(string.last().isDigit())
                            string.last().digitToInt()
                        else
                            digits.keys.firstOrNull { string.contains(it) }?.let { digits[it] }
                    }
                val last = string.indices
                    .reversed()
                    .map { string.substring(it) }
                    .firstNotNullOf { string ->
                        if(string.first().isDigit())
                            string.first().digitToInt()
                        else
                            digits.keys.firstOrNull { string.contains(it) }?.let { digits[it] }
                    }

                "$first$last".toInt()
            }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    val testInput2 = readInput("Day01_test2")
    check(part1(testInput) == 142)
    check(part2(testInput2) == 281)


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
