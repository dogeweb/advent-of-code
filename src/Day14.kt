import kotlin.time.measureTimedValue

fun main() {

    data class Robot(var px: Int, var py: Int, val vx: Int, val vy: Int)

    fun parseInput(input: List<String>) =
        "(\\d+),(\\d+) v=(-*\\d+),(-*\\d+)".toRegex().let { regex ->
            input
                .mapNotNull(regex::find)
                .map { it.groupValues.drop(1).map { it.toInt() }
                    .let { (a, b, c, d) -> Robot(a, b, c, d) }
                }
        }

    fun printBots(bots: List<Robot>, bounds: Pair<Int, Int>) {
        (0 ..< bounds.second).forEach { b1 ->
            (0 ..< bounds.first).map { b2 -> bots.count { it.px == b2 && it.py == b1 } }
                .joinToString("") { if (it == 0) "." else "$it" }
                .println()
        }
    }

    fun part1(input: List<String>, bounds: Pair<Int, Int>): Int {
        val bots = parseInput(input)

        repeat(100) {
            bots.forEach {
                it.px = ((it.px + it.vx) + bounds.first) % bounds.first
                it.py = ((it.py + it.vy) + bounds.second) % bounds.second
            }
        }

        val hHalf = bounds.first / 2
        val vHalf = bounds.second / 2

        return listOf(
            0 until hHalf to (0 until vHalf),
            0 until hHalf to (vHalf + 1 until bounds.second),
            hHalf + 1 until bounds.first to (vHalf + 1 until bounds.second),
            hHalf + 1 until bounds.first to (0 until vHalf),
        ).map { b -> bots.count { it.px in b.first && it.py in b.second } }
            .let { (a, b, c, d) -> a * b * c * d }
    }

    fun part2(input: List<String>, bounds: Pair<Int, Int>): Int {
        val bots = parseInput(input)

        repeat(100000) {
            bots.forEach {
                it.px = ((it.px + it.vx) + bounds.first) % bounds.first
                it.py = ((it.py + it.vy) + bounds.second) % bounds.second
            }
            if (bots.distinctBy { it.px to it.py }.count() == bots.count()) {
                return it + 1
            }
        }

        return -1
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    val testInput = readInput("Day14_test")
    // Or read a large test input from the `src/Day01_test.txt` file:
    check(part1(testInput, 11 to 7).apply { println() } == 12)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day14")
    measureTimedValue { part1(input, 101 to 103) }.println()
    measureTimedValue { part2(input, 101 to 103) }.println()
}
