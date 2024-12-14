import kotlin.time.measureTimedValue

fun main() {

    data class Point(val x: Int, val y: Int)

    fun part1(input: List<String>): Int {
        val bounds = input.indices to input[0].indices
        val zeros = input.flatMapIndexed { index, it ->
            "0".toRegex().findAll(it).flatMap { it.groups }.mapNotNull { it }.map { Point(it.range.first, index) }
        }

        fun paths(num: Int, x: Int, y: Int): List<Point> {
            if (num == 9) return listOf(Point(x, y))
            return listOf((x - 1 to y), (x + 1 to y), (x to y + 1), (x to y - 1))
                .flatMap { (a, b) ->
                    if (a in bounds.second && b in bounds.first && input[b][a].digitToInt() == num + 1) {
                        paths(num + 1, a, b)
                    } else emptyList()
                }
        }
        return zeros.sumOf {
            paths(0, it.x, it.y).distinct().size
        }
    }

    fun part2(input: List<String>): Int {
        val bounds = input.indices to input[0].indices
        val zeros = input.flatMapIndexed { index, it ->
            "0".toRegex().findAll(it).flatMap { it.groups }.mapNotNull { it }.map { Point(it.range.first, index) }
        }

        fun paths(num: Int, x: Int, y: Int): Int {
            if (num == 9) return 1
            return listOf((x - 1 to y), (x + 1 to y), (x to y + 1), (x to y - 1))
                .sumOf { (a, b) ->
                    if (a in bounds.second && b in bounds.first && input[b][a].digitToInt() == num + 1) {
                        paths(num + 1, a, b)
                    } else 0
                }
        }
        return zeros.sumOf {
            paths(0, it.x, it.y)
        }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
//    val testInput = readInput("Day01_test")
    val testInput2 = readInput("Day10_test2")
    check(part1(testInput2).apply { println() } == 36)
    check(part2(testInput2).apply { println() } == 81)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day10")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
