import java.lang.reflect.Array.set
import java.util.stream.Collectors.toSet
import kotlin.time.measureTimedValue

fun main() {

    fun part1(input: List<String>): Int {
        val map = mutableMapOf<String, MutableSet<String>>()
        input.forEach {
            it.split("-").let { (a, b) ->
                map.getOrPut(a) { mutableSetOf() }.add(b)
                map.getOrPut(b) { mutableSetOf() }.add(a)
            }
        }

        return buildSet {
            map.forEach {
                it.value.map { v -> v to map.getValue(v).filter { k -> map.getValue(k).contains(it.key) } }
                    .forEach { (v, k) ->
                        k.forEach { kz -> add(setOf(it.key, v, kz)) }
                    }
            }
        }.filter { it.any { it.startsWith("t") } }.size
    }


    fun part2(input: List<String>): String {
        val map = mutableMapOf<String, MutableSet<String>>().withDefault { mutableSetOf() }
        input.forEach {
            it.split("-").let { (a, b) ->
                map.getOrPut(a) { mutableSetOf() }.apply { add(b) }
                map.getOrPut(b) { mutableSetOf() }.apply { add(a) }
            }
        }

        var largestClique = emptySet<String>()

        fun bronKerbosch(R: Set<String>, P: MutableSet<String>, X: MutableSet<String>) {
            if (P.isEmpty() && X.isEmpty()) {
                if (R.size > largestClique.size) {
                    largestClique = R
                }
                return
            }
            val u = (P + X).maxByOrNull { map[it]?.size ?: 0 } ?: return
            for (v in P - (map[u] ?: emptySet()).toSet()) {
                bronKerbosch(
                    R + v,
                    P.intersect(map.getValue(v)).toMutableSet(),
                    X.intersect(map.getValue(v)).toMutableSet()
                )
                P.remove(v)
                X.add(v)
            }
        }

        bronKerbosch(emptySet(), map.keys.toMutableSet(), mutableSetOf())

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
