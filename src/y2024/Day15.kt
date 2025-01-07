package y2024

import kotlin.time.measureTimedValue

fun main() {

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = this.first + other.first to this.second + other.second

    fun next(pos: Pair<Int, Int>, char: Char) =
        when (char) {
            '^' ->  0 to -1
            '>' ->  1 to  0
            '<' -> -1 to  0
            'v' ->  0 to  1
            else -> throw Exception("Unexpected character: $char")
        } + pos

    fun next2D(pos: Pair<Int, Int>, char: Char) =
        when (char) {
            '^' -> listOf( 0 to -1, -1 to -1)
            '>' -> listOf( 1 to 0)
            '<' -> listOf(-2 to 0)
            'v' -> listOf( 0 to  1, -1 to 1)
            else -> throw Exception("Unexpected character: $char")
        }.map { pos + it }.toSet()

    fun next2DBox(pos: Pair<Int, Int>, char: Char) =
        when (char) {
            '^' -> listOf( 0 to -1, -1 to -1, 1 to -1)
            '>' -> listOf( 2 to  0)
            '<' -> listOf(-2 to  0)
            'v' -> listOf( 0 to  1, 1 to 1, -1 to 1)
            else -> throw Exception("Unexpected character: $char")
        }.map { pos + it }.toSet()

    fun printAll(walls: Set<Pair<Int, Int>>, boxes: Set<Pair<Int, Int>>, robot: Pair<Int, Int>) {
        val maxX = walls.maxOf { it.first }
        val maxY = walls.maxOf { it.second }

        (0..maxY).forEach { y ->
            (0..maxX).map { x ->
                when (x to y) {
                    in walls -> '#'
                    in boxes -> 'O'
                    robot -> '@'
                    else -> '.'
                }
            }.joinToString("").println()
        }
    }

    fun printAll2D(walls: Set<Pair<Int, Int>>, boxes: Set<Pair<Int, Int>>, robot: Pair<Int, Int>) {
        val maxX = walls.maxOf { it.first }
        val maxY = walls.maxOf { it.second }

        (0..maxY).forEach { y ->
            var skipNext = false
            (0..maxX).map { x ->
                if (skipNext) { skipNext= false; return@map ""}
                when (x to y) {
                    robot -> '@'
                    in walls -> { skipNext = true; "##" }
                    in boxes -> { skipNext = true; "[]" }
                    else -> "."
                }
            }.joinToString("").println()
        }
    }

    fun part1(input: List<String>): Int {

        val walls = mutableSetOf<Pair<Int, Int>>()
        val boxes = mutableSetOf<Pair<Int, Int>>()
        var robot = Pair(0, 0)

        input.takeWhile { it.isNotBlank() }.forEachIndexed { y, it ->
            it.forEachIndexed { x, it ->
                when (it) {
                    '#' -> walls += x to y
                    'O' -> boxes += x to y
                    '@' -> robot = x to y
                }
            }
        }

        val instr = input.drop(input.indexOfFirst { it.isBlank() } + 1)
            .joinToString("")

        instr.forEach { c ->
            var n = next(robot, c)
            if (n in walls) return@forEach
            val obst = mutableSetOf<Pair<Int, Int>>()
            while (n !in walls && n in boxes) {
                obst += n
                n = next(n, c)
            }
            if (n !in boxes && n !in walls) {
                boxes.removeAll(obst)
                boxes.addAll(obst.map { next(it, c) })
                robot = next(robot, c)
            }
        }

//        printAll(walls, boxes, robot)

        return boxes.sumOf { it.first + it.second * 100 }
    }

    fun part2(input: List<String>): Int {
        val walls = mutableSetOf<Pair<Int, Int>>()
        val boxes = mutableSetOf<Pair<Int, Int>>()
        var robot = Pair(0, 0)

        input.takeWhile { it.isNotBlank() }.forEachIndexed { y, it ->
            input.takeWhile { it.isNotBlank() }.forEachIndexed { y, it ->
                it.forEachIndexed { x, it ->
                    when (it) {
                        '#' -> walls += 2 * x to y
                        'O' -> boxes += 2 * x to y
                        '@' -> robot = 2 * x to y
                    }
                }
            }
        }

        input.drop(input.indexOfFirst { it.isBlank() } + 1)
            .joinToString("")
            .forEach { c ->
                var n = next2D(robot, c)
                if (next(robot, c) in walls) return@forEach
                val obst = mutableSetOf<Pair<Int, Int>>()
                while (walls.intersect(n).isEmpty()) {
                    val f = boxes.intersect(n)
                    if (f.isEmpty()) break
                    obst.addAll(f)
                    n = f.flatMap { next2DBox(it, c) }.toSet()
                }

                if (boxes.intersect(n).isEmpty() && walls.intersect(n).isEmpty()) {
                    boxes.removeAll(obst)
                    boxes.addAll(obst.map { next(it, c) })
                    robot = next(robot, c)
                }
            }

//        printAll2D(walls, boxes, robot)

        return boxes.sumOf { it.first + it.second * 100 }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day15_test")
    check(part1(testInput).apply { println() } == 10092)
    check(part2(testInput).apply { println() } == 9021)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day15")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
