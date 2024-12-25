
fun main() {

    fun parseInput(input: List<String>) =
        input.asSequence()
            .map {
                val cal = it.substringBefore(":").toLong()
                val nums = it.substringAfter(":").trim().split(" ").map { it.toLong() }
                cal to nums
            }

    fun part1(input: List<String>): Long {
        fun resolve(target: Long, list: List<Long>): Boolean {
            if (list.size == 1) {
                return list[0] == target
            }
            return resolve(target, listOf(list[0] + list[1]) + list.drop(2))
                    || resolve(target, listOf(list[0] * list[1]) + list.drop(2))
        }
        return parseInput(input).sumOf { (cal, nums) ->
            if(resolve(cal, nums)) cal else 0
        }
    }

    fun part2(input: List<String>): Long {
        fun resolve(target: Long, list: List<Long>): Boolean {
            if (list.size == 1) {
                return list[0] == target
            }
            return resolve(target, listOf(list[0] + list[1]) + list.drop(2))
                    || resolve(target, listOf(list[0] * list[1]) + list.drop(2))
                    || resolve(target, listOf("${list[0]}${list[1]}".toLong()) + list.drop(2))
        }
        return parseInput(input).sumOf { (cal, nums) ->
            if(resolve(cal, nums)) cal else 0
        }
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput).also { println(it) } == 3749L)
    check(part2(testInput).also { println(it) } == 11387L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
