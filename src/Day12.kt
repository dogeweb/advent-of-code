import kotlin.time.measureTimedValue

fun main() {

    fun findGroups(input: List<String>): List<Set<Pair<Int, Int>>> {
        val (vBounds, hBounds) = input.indices to input[0].indices

        val visited = mutableSetOf<Pair<Int, Int>>()

        val rec = DeepRecursiveFunction<Pair<Int, Int>, List<Pair<Int, Int>>> {
            val (x, y) = it
            val c = input[y][x]
            listOf(it) + listOf(x + 1 to y, x - 1 to y, x to y + 1, x to y - 1)
                .filter { (x1, y1) -> x1 in hBounds && y1 in vBounds && input[y1][x1] == c }
                .filter(visited::add)
                .flatMap { callRecursive(it) }
        }

        return input.indices
            .asSequence()
            .flatMap { i -> input[i].indices.map { it to i } }
            .filter(visited::add)
            .map { rec(it).toSet() }
            .toList()
    }

    fun <T> List<T>.distinctUntilChanged() =
        listOfNotNull(firstOrNull()) + zipWithNext().filter { it.first != it.second }.map { it.second }

    fun Set<Pair<Int, Int>>.fenceCost(discount: Boolean = false) = let {
        val xb = it.minOf { it.first } .. it.maxOf { it.first }
        val yb = it.minOf { it.second } .. it.maxOf { it.second }

        listOf((xb.first - 1 .. xb.last + 1).map { x -> yb.map { y -> x to y in it } },
            (yb.first - 1 .. yb.last + 1).map { y -> xb.map { x -> x to y in it } })
            .sumOf {
                it.zipWithNext()
                    .sumOf {
                        it.first.zip(it.second)
                            .let { if (discount) it.distinctUntilChanged() else it }
                            .count { it.first != it.second }
                    }
            } * it.count()
    }

    fun part1(input: List<String>) = findGroups(input).sumOf { it.fenceCost() }

    fun part2(input: List<String>) = findGroups(input).sumOf { it.fenceCost(true) }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day12_test")
    val testInput2 = readInput("Day12_test2")
    val testInput3 = readInput("Day12_test3")
    val testInput4 = readInput("Day12_test4")
    val testInput5 = readInput("Day12_test5")
    check(part1(testInput).apply { println() } == 140)
    check(part1(testInput2).apply { println() } == 1930)
    check(part2(testInput).apply { println() } == 80)
    check(part2(testInput2).apply { println() } == 1206)
    check(part2(testInput3).apply { println() } == 368)
    check(part2(testInput4).apply { println() } == 236)
    check(part2(testInput5).apply { println() } == 436)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day12")
    measureTimedValue { part1(input).also { check(it == 1485656) }}.println()
    measureTimedValue { part2(input).also { check(it == 899196) }}.println()
}
