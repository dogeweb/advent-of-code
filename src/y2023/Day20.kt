package y2023

import java.util.*
import kotlin.time.measureTimedValue

fun main() {

    fun solve(input: List<String>, entry: String = "broadcaster", output: String = "", part2: Boolean = false): Long {

        val map = input.map { it.split(" -> ") }
            .associate { it[0].trimStart('%', '&') to (it[0].firstOrNull { it in "%&" } to it[1].split(", ")) }

        val state = mutableMapOf<String, Boolean>().withDefault { false }

        val conj = mutableMapOf<String, MutableMap<String, Boolean>>().run {
            withDefault { getOrPut(it) { mutableMapOf<String, Boolean>().withDefault { false } } }
        }

        map.filterValues { it.first == '&' }.keys.forEach { key ->
            map.filterValues { key in it.second }.keys.forEach { conj.getValue(key)[it] = false }
        }

        val queue = ArrayDeque<Triple<String, String, Boolean>>()

        var (count, low, high) = Triple(0L, 0L, 0L)

        generateSequence {
            queue.poll() ?: if (part2 || count < 1000) {
                count++
                Triple("button", entry, false)
            } else null
        }
        .run {
            if (part2) takeWhile { (_, pulseTarget, pulse) -> pulse || pulseTarget != output }
            else onEach { (_, _, pulse) -> if (pulse) high++ else low++ }
        }
        .forEach { (pulseFrom, pulseTarget, pulse) ->
            map[pulseTarget]?.let { (type, targets) ->
                when (type) {
                    '%' if (!pulse) -> state.compute(pulseTarget) { _, v -> v?.not() ?: true }
                    '&' -> conj.getValue(pulseTarget).also { it[pulseFrom] = pulse }.any { !it.value }
                    null -> pulse
                    else -> null
                }?.run { targets.map { Triple(pulseTarget, it, this) } }
                ?.let(queue::addAll)
            }
        }

        return if (part2) count else low * high.toLong()
    }

    fun part1(input: List<String>) = solve(input)

    fun part2(input: List<String>) =
        sequenceOf("np", "qd", "mg", "dp", "vd", "dh", "xr" , "bb")
        .chunked(2) { (entry, output) -> solve(input, entry, output, true) }
        .reduce(Long::times)

    val testInput = readInput("Day20_test")
    val testInput2 = readInput("Day20_test2")
    check(part1(testInput).apply { println() } == 32000000L)
    check(part1(testInput2).apply { println() } == 11687500L)

    val input = readInput("Day20")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
