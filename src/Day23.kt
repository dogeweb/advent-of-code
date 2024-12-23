import kotlin.time.measureTimedValue

fun main() {

    fun parseInput(input: List<String>): MutableMap<String, MutableSet<String>> {
        val map = mutableMapOf<String, MutableSet<String>>()
        input.forEach {
            it.split("-").let { (a, b) ->
                map.getOrPut(a) { mutableSetOf() }.add(b)
                map.getOrPut(b) { mutableSetOf() }.add(a)
            }
        }
        return map
    }

    fun part1(input: List<String>): Int {
        val map = parseInput(input)

        return buildSet {
            map.forEach {
                it.value.map { v -> v to map[v]!!.filter { k -> it.key in map[k]!! } }
                    .forEach { (v, k) -> k.forEach { kz -> add(setOf(it.key, v, kz)) } }
            }
        }.count { it.any { it.startsWith("t") } }
    }


    fun part2(input: List<String>): String {
        val neighbours = parseInput(input)

        var largestClique = emptySet<String>()

        fun bronKerbosch(R: Set<String>, P: Set<String>, X: Set<String>) {
            if (P.isEmpty() && X.isEmpty()) {
                if (R.size > largestClique.size) largestClique = R
                return
            }
            val u = (P + X).maxByOrNull { neighbours[it]!!.size } ?: return
            val p = P.toMutableSet()
            val x = X.toMutableSet()
            for (v in P - neighbours[u]!!) {
                neighbours[v]!!.let { bronKerbosch(R + v, p.intersect(it), x.intersect(it)) }
                p.remove(v)
                x.add(v)
            }
        }

        bronKerbosch(emptySet(), neighbours.keys, emptySet())

        return largestClique.sorted().joinToString(",")
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day23_test")
    check(part1(testInput).apply { println() } == 7)
    check(part2(testInput).apply { println() } == "co,de,ka,ta")

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day23")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
