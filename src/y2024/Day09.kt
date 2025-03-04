package y2024

import kotlin.math.max
import kotlin.time.TimeSource
import kotlin.time.TimeSource.Monotonic.ValueTimeMark
import kotlin.time.measureTimedValue

fun main() {

    data class MutablePair(var first: Int, var second: Int = -1)

    val timeSource = TimeSource.Monotonic

    fun part1(input: List<String>): Long {

        val marks = mutableListOf<ValueTimeMark>()
        marks.add(timeSource.markNow())

        val arr = input.first()
            .chunked(2)
            .flatMapIndexedTo(ArrayList(input.first().length)) { index, s ->
                if (s.length == 1) List(s[0].digitToInt()) { index }
                else List(s[0].digitToInt()) { index } + List(s[1].digitToInt()) { -1 }
        }

        marks.add(timeSource.markNow())

        var (left, right) = 0 to arr.size - 1

        while (left < right) {
            if (arr[left]  != -1) left++
            if (arr[right] == -1) right--
            if (arr[left]  == -1 && arr[right] != -1) {
                val t = arr[left]
                arr[left] = arr[right]
                arr[right] = t
            }
        }

        marks.add(timeSource.markNow())

        val r = arr.mapIndexed { index, i -> if (i > 0) (index * i).toLong() else 0 }.sum()

        marks.add(timeSource.markNow())

        print("part1 ")
        marks
            .zipWithNext { a, b -> b - a }
            .println()

        return r
    }

    fun part2(input: List<String>): Long {

        val marks = mutableListOf<ValueTimeMark>()
        marks.add(timeSource.markNow())

        var maxBlock = 0

        val arr = input.first()
            .chunked(2)
            .flatMapIndexedTo(ArrayList(input.first().length)) { index, s ->
                if (s.length == 1) listOf(MutablePair(s[0].digitToInt(), index))
                else listOf(
                    MutablePair(s[0].digitToInt().also { maxBlock = max(maxBlock, it) }, index),
                    MutablePair(s[1].digitToInt()))
            }

        marks.add(timeSource.markNow())

        var lastBlockIndex = arr.size
        val pos = IntArray(maxBlock + 1) { 0 }

        arr
            .asReversed()
            .filter { it.second != -1 }
            .forEach { block ->
                val spaceOffset = pos[block.first]
                if (spaceOffset > lastBlockIndex) return@forEach

                lastBlockIndex = spaceOffset + (arr.subList(spaceOffset, lastBlockIndex).lastIndexOf(block)
                    .takeUnless { it == -1 } ?: return@forEach)

                val firstSpaceIndex = arr.subList(spaceOffset, lastBlockIndex)
                    .indexOfFirst { it.second == -1 && it.first >= block.first }
                    .takeUnless { it == -1 }
                    ?.let { it + spaceOffset }
                    ?.apply { (block.first..maxBlock).forEach { pos[it] = max(pos[it], this + 1) } }
                    ?: run { (block.first..maxBlock).forEach { pos[it] = Int.MAX_VALUE }; return@forEach }

                val space = arr[firstSpaceIndex].first

                arr[firstSpaceIndex].first  = block.first
                arr[firstSpaceIndex].second = block.second
                arr[lastBlockIndex].second = -1

                if (space > block.first)
                    arr.add(firstSpaceIndex + 1, MutablePair(space - block.first))
            }

        marks.add(timeSource.markNow())

        return arr
            .dropLastWhile { it.second == -1 }
            .flatMap { (a, b) -> List(a) { b.toLong() } }
            .reduceIndexed { index, acc, i -> acc + if (i > 0) (index * i) else 0L }
            .also {
                marks.add(timeSource.markNow())
                print("part2 ")
                marks
                    .zipWithNext { a, b -> b - a }
                    .println()
            }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day09_test")
    check(part1(testInput).also { println(it) } == 1928L)
    check(part2(testInput).also { println(it) } == 2858L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day09")
    measureTimedValue { part1(input).also { check(it == 6201130364722) } }.println()
    measureTimedValue { part2(input).also { check(it == 6221662795602) } }.println()
}
