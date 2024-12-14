fun main() {
    fun part1(order: List<String>, updates: List<String>): Int {
        val o = order.map { it.split("|").map(String::toInt) }.map { (a, b) -> a to b }
        return updates
            .map { it.split(",").map(String::toInt) }
            .filter {
                o.all { (a, b) ->
                    if(a in it && b in it) it.indexOf(a) < it.indexOf(b) else true
                }
            }
            .sumOf { it[it.size / 2] }
    }

    fun part2(order: List<String>, updates: List<String>): Int {
        val o = order.map { it.split("|").map(String::toInt) }.map { (a, b) -> a to b }.toSet()
        return updates
            .asSequence()
            .map { it.split(",").map(String::toInt).mapIndexed { index, i -> i to index }.toMap() }
            .filter {
                !o.all { (a, b) -> if(a in it && b in it) it[a]!! < it[b]!! else true }
            }
            .sumOf {
                it.keys.sortedWith { a, b ->
                    when {
                        a to b in o -> -1
                        b to a in o -> 1
                        else -> 0
                    }
                }[it.size / 2]
            }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val orderInput = readInput("Day05_test1")
    val updatesInput = readInput("Day05_test2")
    check(part1(orderInput, updatesInput) == 143)
    check(part2(orderInput, updatesInput) == 123)

    // Read the input from the `src/Day01.txt` file.
    val order   = readInput("Day05_1")
    val updates = readInput("Day05_2")
    part1(order, updates).println()
    part2(order, updates).println()
//    part2(input).println()
}
