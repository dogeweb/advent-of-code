fun main() {
    fun part1(input: List<String>): Int {
        val regex = "mul\\((\\d*),(\\d*)\\)".toRegex()
        return input.sumOf { regex.findAll(it).map { it.groupValues.drop(1).map { it.toInt() }.reduce(Int::times) }.sum() }
    }

    fun part2(input: List<String>): Int {
        val regex = "mul\\((\\d*),(\\d*)\\)".toRegex()
        var s = input.joinToString("")
        return sequence {
            while (s.isNotBlank()) {
                yield(s.substringBefore("don't()"))
                s = s.substringAfter("don't()", "").substringAfter("do()", "")
            }
        }.sumOf { regex.findAll(it).map { it.groupValues.let { it[1].toInt() * it[2].toInt() } }.sum() }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    val testInput2 = readInput("Day03_test2")
    check(part1(testInput) == 161)
    check(part2(testInput2) == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
