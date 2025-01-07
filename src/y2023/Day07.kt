package y2023

import kotlin.time.measureTimedValue

fun main() {

    fun handType(hand: String, specialJ: Boolean = false): Int {
        val groups = hand.groupingBy { it }.eachCount()
            .let {
                if (specialJ) {
                    val map = it.toMutableMap()
                    if (map['J'] != 5)
                        map.remove('J')?.let { j -> map.maxBy { it.value }.key.let { map[it] = map[it]!! + j } }
                    map
                } else it
            }
            .map { it.value }.toList().sortedDescending()
        return when {
            groups[0] == 5 -> 6
            groups[0] == 4 -> 5
            groups[0] == 3 && groups[1] == 2 -> 4
            groups[0] == 3 && groups[1] == 1 -> 3
            groups[0] == 2 && groups[1] == 2 -> 2
            groups[0] == 2 && groups[1] == 1 -> 1
            groups[0] == 1 -> 0
            else -> error("Invalid hand")
        }
    }

    fun part1(input: List<String>): Int {
        val order = "AKQJT98765432".reversed()
        val hands = input.map { it.split(" ").let { (a, b) -> a to b.toInt() } }
        return hands.sortedWith(compareBy({ handType(it.first) }, { it.first.map { order.indexOf(it).toChar() }.joinToString("") }))
            .mapIndexed { i, (_, bid) -> (i + 1) * bid }.sum()
    }

    fun part2(input: List<String>): Int {
        val order = "AKQT98765432J".reversed()
        val hands = input.map { it.split(" ").let { (a, b) -> a to b.toInt() } }
        return hands.sortedWith(compareBy({ handType(it.first, true) }, { it.first.map { order.indexOf(it).toChar() }.joinToString("") }))
            .mapIndexed { i, (_, bid) -> (i + 1) * bid }.sum()
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput).apply { println() } == 6440)
    check(part2(testInput).apply { println() } == 5905)

    val input = readInput("Day07")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
