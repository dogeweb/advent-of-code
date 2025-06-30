package y2023

import y2024.println

fun main() {

    fun part1(input: List<String>): Int {
        val symbols = "*#-+@%&=$/"
        var sum = 0
        input.forEachIndexed { vert, c ->
            var s = ""
            ("$c.").forEachIndexed { horiz, it ->
                if (it.isDigit()) s += it
                else {
                     if (s.isNotBlank()) {
                        val h = (vert - 1..vert + 1)
                        val l = (horiz - s.length - 1..horiz)
                        loop@ for (hv in h) {
                            if (hv in input.indices) {
                                for (lv in l) {
                                    if (lv in input[hv].indices)
                                        if(input[hv][lv] in symbols) {
                                            sum += s.toInt()
                                            break@loop
                                        }
                                }
                            }
                        }
                    }
                    s = ""
                }
            }
        }
        return sum
    }

    data class Match(val value: String, val range: IntRange, val height: Int)

    fun part2(input: List<String>): Int {
        val nums = input.flatMapIndexed { vert, c ->
            "\\d+".toRegex().findAll(c)
                .flatMap { it.groups }
                .map { Match(it!!.value, it.range, vert) }
        }.toMutableSet()
        val gears = input.flatMapIndexed { vert, c ->
            "\\*".toRegex().findAll(c)
                .flatMap { it.groups }
                .map { Match(it!!.value, it.range, vert) }
        }.toMutableSet()
        return gears.sumOf { g ->
            nums.filter { g.range.first in it.range.first - 1.. it.range.last + 1 && it.height in g.height - 1 .. g.height + 1 }
                .takeIf { it.size > 1 }
                ?.take(2)
                ?.also { nums.removeAll(it.toSet()) }
                ?.let { (a, b) -> a.value.toInt() * b.value.toInt() }
                ?: 0
        }
    }

    val testInput = readInput("Day03_test")

    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
