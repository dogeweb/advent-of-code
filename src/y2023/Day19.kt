package y2023

import kotlin.time.measureTimedValue

fun main() {

    data class Condition(val category: Int = 0, val operation: Int = 0, val value: Int = 0, val destination: String)
    data class Workflow(val conditions: List<Condition>, val end: String)

    val regex = "(\\w+)\\{((?:[xmas][<>]\\d+:\\w+,?)+)?(\\w+)}".toRegex()
    val conditionsRegex = "([xmas])([<>])(\\d+):(\\w+)".toRegex()

    fun parseInput(input: List<String>, part2: Boolean = false) = input.run {
        val workflows = asSequence().takeWhile { it.isNotBlank() }
            .mapNotNull(regex::find)
            .map(MatchResult::destructured)
            .associate { (name, c, end) ->
                name to Workflow(conditionsRegex.findAll(c)
                        .map { it.destructured }
                        .mapTo(mutableListOf()) { (a, b, c, d) ->
                            Condition("xmas".indexOf(a[0]), if (b[0] == '<') -1 else 1, c.toInt(), d)
                        }, end)
            }

        val parts = if (!part2)
            subList(workflows.size + 1, input.size)
                .map { it.substring(1, it.length - 1).split(',').map { it.substring(2).toInt() }.toIntArray() }
        else emptyList()

        workflows to parts
    }

    fun part1(input: List<String>) = parseInput(input).let { (workflows, parts) ->
        parts.filter { part ->
            "A" == generateSequence("in") {
                workflows[it]?.run {
                    conditions.firstOrNull { part[it.category].compareTo(it.value) == it.operation }?.destination ?: end
                }
            }.last()
        }.sumOf { it.sum() }
    }


    fun part2(input: List<String>) = buildList {
        val (workflows, _) = parseInput(input, true)
        DeepRecursiveFunction { (wf, part): Pair<Workflow, Array<IntRange>> ->
            for ((category, operation, value, destination) in wf.conditions + Condition(destination = wf.end)) {
                listOf(part.copyOf(), part).zip(part[category].run {
                        when (operation) {
                            -1 -> first..<value to value..last
                            +1 -> value + 1..last to first..value
                            else -> to(IntRange.EMPTY)
                        }.toList() })
                .onEach { (a, b) -> a[category] = b }.first().first.let {
                    when (destination) {
                        "R" -> Unit
                        "A" -> add(it)
                        else -> callRecursive(workflows[destination]!! to it)
                    }
                }
            }
        }(workflows["in"]!! to Array(4) { 1..4000 })
    }
    .sumOf { it.map { it.last - it.start + 1L }.reduce(Long::times) }

    val testInput = readInput("Day19_test")
    check(part1(testInput).apply { println() } == 19114)
    check(part2(testInput).apply { println() } == 167409079868000)

    val input = readInput("Day19")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
