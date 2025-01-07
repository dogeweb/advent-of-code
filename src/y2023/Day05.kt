package y2023

import y2024.println
import kotlin.math.max
import kotlin.math.min
import kotlin.time.measureTimedValue

fun main() {

    fun LongRange.intersect(other: LongRange): LongRange {
        return LongRange(max(first, other.first), min(last, other.last))
    }

    fun LongRange.isNotEmpty() = !isEmpty()

    fun resolveSeed(seed: Long, steps: List<List<Pair<LongRange, LongRange>>>): Long {
        return steps.fold(seed) { acc, s2s -> s2s.first { z -> acc in z.second }.let { z -> z.first.start + (acc - z.second.start) } }
    }

 /*   fun resolveSeedReversed(seed: Long, steps: List<List<Pair<LongRange, LongRange>>>): Long {
        return steps.fold(seed) { acc, s2s -> s2s.first { z -> acc in z.first }.let { z -> z.second.start + (acc - z.first.start) } }
    }*/

    fun parseMap(input: List<String>, string: String) = input
        .dropWhile { it != string }
        .drop(1)
        .takeWhile { it.isNotBlank() }
        .map { it.split(" ").map { it.toLong() } }
        .map { (dest, start, length) -> Triple(dest, start, length) }
        .sortedBy { it.second }
        .zipWithNext()
        .flatMap { (a, b) -> if(a.second + a.third + 1 != b.second) listOf(a, Triple(a.second + a.third + 1, a.second + a.third + 1, b.second - a.second - a.third), b) else listOf(a, b) }
        .sortedBy { it.second }
        .let { it + Triple(it.last().second + it.last().third, it.last().second + it.last().third, Long.MAX_VALUE - it.last().second - it.last().third) }
        .let { if(it.first().second == 0L) it else it + Triple(0L, 0L, it.first().second) }
        .map { it.first..< it.first + it.third to it.second ..< it.second + it.third }
        .sortedBy { it.second.start }

    fun parseSteps(input: List<String>) =
        listOf(
            "seed-to-soil map:",
            "soil-to-fertilizer map:",
            "fertilizer-to-water map:",
            "water-to-light map:",
            "light-to-temperature map:",
            "temperature-to-humidity map:",
            "humidity-to-location map:"
        ).map { parseMap(input, it) }

    fun part1(input: List<String>): Long {
        val seeds = input.first().removePrefix("seeds: ").split(" ").map { it.toLong() }
        val steps = parseSteps(input)

        return seeds.minOf { resolveSeed(it, steps) }
    }

    fun part2(input: List<String>): Long {
        val seeds = input.first().removePrefix("seeds: ").split(" ").map { it.toLong() }.chunked(2) { (a, b) -> a ..< a + b }
        val steps = parseSteps(input)
        val reversedSteps = steps.reversed()

        /*
        brute-force:
        repeat(Int.MAX_VALUE) { loc ->
                resolveSeedReversed(loc.toLong(), reversedSteps).let { z ->
                    if(seeds.any { z in it }) return loc.toLong()
                }
            }
        */

        val rec = DeepRecursiveFunction<Pair<LongRange, Int>, List<Long>> { (range, depth) ->
            if (depth == reversedSteps.size)
                seeds.mapNotNull { it.intersect(range).takeIf { it.isNotEmpty()}?.start }
            else reversedSteps[depth]
                .filter { it.first.intersect(range).isNotEmpty() }
                .flatMap {
                    val int = it.first.intersect(range)
                    callRecursive(Pair(it.second.start + max(int.start - it.first.start, 0) .. it.second.last - max(it.first.last - int.last, 0), depth + 1))
                }
        }

        return steps.last()
            .sortedBy { it.first.start }
            .flatMap { rec(it.second to 1) }
            .minOf { resolveSeed(it, steps) }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput).apply { println() } == 35L)
    check(part2(testInput).apply { println() } == 46L)

    val input = readInput("Day05")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
