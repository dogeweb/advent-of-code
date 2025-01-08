package y2023

import kotlin.time.measureTimedValue

fun main() {

    fun solve(seq: String, instr:  Map<String, Pair<String, String>>, start: String): Sequence<Pair<String, Int>> {
        return generateSequence(start to 0) { (curr, steps) ->
            instr[curr]!!.let { if (seq[steps % seq.length] == 'L') it.first else it.second } to steps + 1
        }
    }

    fun parseInput(input: List<String>) =
        "(\\w+) = \\((\\w+), (\\w+)\\)".toRegex().let { regex ->
            input[0] to input.subList(2, input.size)
                .mapNotNull(regex::find)
                .associate { it.destructured.let { (a, b, c) -> a to Pair(b, c) } }
        }

    tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

    fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b 

    fun part1(input: List<String>) =
        parseInput(input).let { (seq, instr) -> solve(seq, instr, "AAA").indexOfFirst { it.first == "ZZZ" } }

    fun part2(input: List<String>) =
        parseInput(input).let { (seq, instr) ->
            instr.keys.filter { it.endsWith('A') }
                .map { solve(seq, instr, it).indexOfFirst { it.first.endsWith('Z') }.toLong() }
                .reduce(::lcm)
        }

    val testInput = readInput("Day08_test")
    val testInput2 = readInput("Day08_test2")
    val testInput3 = readInput("Day08_test3")
    check(part1(testInput).apply { println() } == 2)
    check(part1(testInput2).apply { println() } == 6)
    check(part2(testInput3).apply { println() } == 6L)

    val input = readInput("Day08")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()

}
