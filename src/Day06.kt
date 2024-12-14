import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

fun main() {
    fun part1(input: List<String>): Int {
        val out = input.map { it.toCharArray() }.toTypedArray()
        var count = 1
        var direction = 0
        val bounds = input.indices to input[0].indices
        var pos = input.mapIndexed { i, j ->
            i to j.indexOfFirst { it == '^' }
        }.first { it.second != -1 }
        out[pos.first][pos.second] = 'X'
        while (pos.first in bounds.first && pos.second in bounds.second) {
            val next = when (direction % 4) {
                0 -> pos.first - 1 to pos.second
                1 -> pos.first to pos.second + 1
                2 -> pos.first + 1 to pos.second
                else -> pos.first to pos.second - 1
            }
            if (next.first in bounds.first && next.second in bounds.second) {
                if (input[next.first][next.second] == '#') {
                    direction += 1
                } else {
                    pos = next
                    if (out[pos.first][pos.second] != 'X') {
                        out[pos.first][pos.second] = 'X'
                        count += 1
                    }
                }
            } else {
                break
            }
        }

//        println(out.joinToString("\n") { it.joinToString("") })
//        println()

        pos.println()
        return count
    }

    fun part2(input: List<String>): Int {
        return input.indices.flatMap { i ->
            input[i].indices.map { j -> i to j }
        }.count { obst ->
            val tdir = AtomicInteger(0)
            val hdir = AtomicInteger(0)
            val bounds = input.indices to input[0].indices
            val t = AtomicReference(input.mapIndexed { i, j -> i to j.indexOfFirst { it == '^' } }.first { it.second != -1 })
            val h = AtomicReference(t.get())

            fun inBound(pos: Pair<Int, Int>) =
                pos.first in bounds.first && pos.second in bounds.second

            fun next(pos: AtomicReference<Pair<Int, Int>>, direction: AtomicInteger): Boolean {
                val next = when (direction.get() % 4) {
                    0 -> pos.get().first - 1 to pos.get().second
                    1 -> pos.get().first     to pos.get().second + 1
                    2 -> pos.get().first + 1 to pos.get().second
                    else -> pos.get().first  to pos.get().second - 1
                }
                if (inBound(next)) {
                    if (next == obst || input[next.first][next.second] == '#') {
                        direction.incrementAndGet()
                        next(pos, direction)
                    } else {
                        pos.set(next)
                    }
                    return true
                }
                return false
            }

            while (true) {
                if(!next(t, tdir)) break
                if(!next(h, hdir)) break
                if(!next(h, hdir)) break
                if (h.get() == t.get() && (hdir.get() % 4 == tdir.get() % 4))
                    return@count true
            }
            false
        }

    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
//    part1(testInput).println()
    check(part1(testInput).also { println(it) } == 41)
    check(part2(testInput).also { println(it) } == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
