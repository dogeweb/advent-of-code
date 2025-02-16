package y2023

import kotlin.time.measureTimedValue

fun main() {

    fun part1(input: List<String>): Int {
        val matrix = input.map { it.toCharArray() }.toTypedArray()
        val dishes = buildList {
            matrix.forEachIndexed { y, rowArray ->
                rowArray.forEachIndexed { x, c -> if (c == 'O') add(x to y) }
            }
        }

        dishes
            .dropWhile { it.second == 0 }
            .forEach {
                var y = it.second
                while (y != 0 && matrix[y - 1][it.first] == '.') { y-- }
                matrix[it.second][it.first] = '.'
                matrix[y][it.first] = 'O'
            }

        var count = 0
        matrix.forEachIndexed { y, rowArray ->
            rowArray.forEachIndexed { x, c -> if (c == 'O') count += matrix.size - y }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val matrix = input.map { it.toCharArray() }.toTypedArray()

        fun getDishes() = buildList {
            matrix.forEachIndexed { y, rowArray ->
                rowArray.forEachIndexed { x, c -> if (c == 'O') add(x to y) }
            }
        }

        fun north() = getDishes()
            .filter { it.second != 0 }
            .sortedBy { it.second }
            .forEach {
                var y = it.second
                while (y != 0 && matrix[y - 1][it.first] == '.') { y-- }
                matrix[it.second][it.first] = '.'
                matrix[y][it.first] = 'O'
            }

        fun east() = getDishes()
            .filter { it.first != matrix[0].lastIndex }
            .sortedBy { -it.first }
            .forEach {
                var x = it.first
                while (x != matrix[0].lastIndex && matrix[it.second][x + 1] == '.') { x++ }
                matrix[it.second][it.first] = '.'
                matrix[it.second][x] = 'O'
            }

        fun south() = getDishes()
            .filter { it.second != matrix.lastIndex }
            .sortedBy { -it.second }
            .forEach {
                var y = it.second
                while (y != matrix.lastIndex && matrix[y + 1][it.first] == '.') { y++ }
                matrix[it.second][it.first] = '.'
                matrix[y][it.first] = 'O'
            }

        fun west() = getDishes()
            .filter { it.first != 0 }
            .sortedBy { it.first }
            .forEach {
                var x = it.first
                while (x != 0 && matrix[it.second][x - 1] == '.') { x-- }
                matrix[it.second][it.first] = '.'
                matrix[it.second][x] = 'O'
            }

//        repeat(1000000000) {
        repeat(1000) {
            north()
            west()
            south()
            east()
        }

        var count = 0
        matrix.forEachIndexed { y, rowArray ->
            rowArray.forEachIndexed { x, c -> if (c == 'O') count += matrix.size - y }
        }
        return count
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput).apply { println() } == 136)
    check(part2(testInput).apply { println() } == 64)

    val input = readInput("Day14")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
