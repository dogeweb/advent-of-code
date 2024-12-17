import kotlin.time.measureTimedValue

fun main() {

    fun runProgram(ax: Int, bx: Int, cx: Int, instrList: List<Pair<Int, Int>>, ): List<Int> {
        var (a, b, c) = Triple(ax, cx, bx)
        var instrPtr = 0
        val out = mutableListOf<Int>()

        while (instrPtr < instrList.size) {
            instrList[instrPtr].let { (op, literal) ->
                val combo by lazy {
                    when (literal) {
                        4 -> a
                        5 -> b
                        6 -> c
                        7 -> throw Exception()
                        else -> literal
                    }.toInt()
                }
                when (op.toInt()) {
                    0 -> a = a.ushr(combo)
                    1 -> b = b xor literal
                    2 -> b = combo % 8
                    3 -> if (a != 0) instrPtr = literal / 2 - 1
                    4 -> b = b xor c
                    5 -> out.add(combo % 8)
                    6 -> b = a.ushr(combo)
                    7 -> c = a.ushr(combo)
                }
            }
            instrPtr++
        }

        return out
    }

    data class Input(val a: Int, val b: Int, val c: Int, val instrList: List<Pair<Int, Int>>)

    fun readInput(input: List<String>): Input {
        var (a, b, c) = input.take(3).map { it.drop(12).toInt() }
        val instrList = input[4].drop(9).split(",").map { it.toInt() }.chunked(2).map { (a, b) -> a to b }
        return Input(a, b, c, instrList)
    }

    fun part1(input: List<String>): String {
        var (a, b, c, instrList) = readInput(input)

        return runProgram(a, b, c, instrList).joinToString(",")
    }

    fun part2(input: List<String>): String {
        var (a, b, c, instrList) = readInput(input)

        fun combo(n: Int) =
            when (n) {
                4 -> a
                5 -> b
                6 -> c
                7 -> throw Exception()
                else -> n
            }

        var last = 0

        return instrList.flatMap { it.toList() }.reversed().map { n ->
            repeat(0b1000) {
                a = last.shl(3) + it
                b = 0
                c = 0
                instrList.forEach { (op, l) ->
                    when (op) {
                        0 -> a = a shr combo(l)
                        1 -> b = b xor l
                        2 -> b = combo(l) % 8
                        4 -> b = b xor c
                        5 -> if (combo(l) % 8 == n) {
                            last = (last.shl(3) + it) and 0b111111
                            return@map it.toString(2).padStart(3, '0')
                        } else return@repeat
                        6 -> b = a shr combo(l)
                        7 -> c = a shr combo(l)
                    }
                }

            }
            throw Exception()
        }.joinToString("").toBigInteger(2).toString()
    }

//     Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day17_test")
    val testInput2 = readInput("Day17_test2")
    check(part1(testInput).apply { println() } == "4,6,3,5,6,3,5,2,1,0")
//    check(part2(testInput2).apply { println() } == "117440")


// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day17")
    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()

//    test()
}
