package y2024

import kotlin.invoke
import kotlin.math.absoluteValue
import kotlin.time.measureTimedValue

fun main() {

    val numberPad = mapOf(
        '7' to (0 to 0),
        '8' to (1 to 0),
        '9' to (2 to 0),
        '4' to (0 to 1),
        '5' to (1 to 1),
        '6' to (2 to 1),
        '1' to (0 to 2),
        '2' to (1 to 2),
        '3' to (2 to 2),
        '0' to (1 to 3),
        'A' to (2 to 3),
    )

    val keyPad = mapOf(
        '^' to (1 to 0),
        'v' to (1 to 1),
        '<' to (0 to 1),
        '>' to (2 to 1),
        'A' to (2 to 0),
    )

    fun numberPadPaths(start: Pair<Int, Int>, end: Pair<Int, Int>): List<List<Char>> {
        if(start == end) return listOf(listOf('A'))
        return buildList {
            if(start.first < end.first) add(numberPadPaths(start.first + 1 to start.second, end).map { listOf('>') + it })
            if(start.first > end.first && start != (1 to 3)) add(numberPadPaths(start.first - 1 to start.second, end).map { listOf('<') + it })
            if(start.second < end.second && start != (0 to 2)) add(numberPadPaths(start.first to start.second + 1, end).map { listOf('v') + it })
            if(start.second > end.second) add(numberPadPaths(start.first to start.second - 1, end).map { listOf('^') + it })
        }.flatten()
    }

    fun keyboardPadPaths(start: Pair<Int, Int>, end: Pair<Int, Int>): List<List<Char>> {
        if(start == end) return listOf(listOf('A'))
        return buildList {
            if(start.first < end.first) add(keyboardPadPaths(start.first + 1 to start.second, end).map { listOf('>') + it })
            if(start.first > end.first && start != (1 to 0)) add(keyboardPadPaths(start.first - 1 to start.second, end).map { listOf('<') + it })
            if(start.second < end.second) add(keyboardPadPaths(start.first to start.second + 1, end).map { listOf('v') + it })
            if(start.second > end.second && start != (0 to 1)) add(keyboardPadPaths(start.first to start.second - 1, end).map { listOf('^') + it })
        }.flatten()
    }

    fun manhattanDistanceAndPress(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        return (start.first - end.first).absoluteValue + (start.second - end.second).absoluteValue + 1
    }

    fun part1(input: List<String>): Int {

        return input.sumOf {
            var last = 'A'
            it.sumOf {
                numberPadPaths(numberPad[last]!!, numberPad[it]!!)
                    .minOf {
                        var last2 = 'A'
                        it.sumOf {
                            keyboardPadPaths(keyPad[last2]!!, keyPad[it]!!)
                                .minOf {
                                    var last3 = 'A'
                                    it.sumOf {
                                        manhattanDistanceAndPress(keyPad[last3]!!, keyPad[it]!!)
                                            .apply { last3 = it }
                                    }
                                }
                                .apply { last2 = it }
                        }
                    }.apply { last = it }
            } * it.filter { it.isDigit() }.toInt()
        }
    }

    fun part2(input: List<String>, robotsCount: Int): Long {

        val map = mutableMapOf<Pair<List<Char>, Int>, Long>()

        val rec = DeepRecursiveFunction<Pair<List<Char>, Int>, Long> { (s, i) ->
            map.getOrPut(s to i) {
                var last = 'A'
                if (i == robotsCount)
                    s.sumOf { char ->
                        manhattanDistanceAndPress(keyPad[last]!!, keyPad[char]!!)
                            .toLong()
                            .apply { last = char }
                    }
                else s.sumOf { char ->
                    keyboardPadPaths(keyPad[last]!!, keyPad[char]!!)
                        .minOf { callRecursive(it to i + 1) }
                        .apply { last = char }
                }
            }
        }

        return input.sumOf {
            var last = 'A'
            it.sumOf { char ->
                numberPadPaths(numberPad[last]!!, numberPad[char]!!)
                    .minOf { rec(it to 1) }
                    .apply { last = char }
            } * it.filter { it.isDigit() }.toLong()
        }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day21_test")
    check(part1(testInput).apply { println() } == 126384)
    part2(testInput, 2).println()
    part2(testInput, 25).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day21")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input, 25) }.println()
}
