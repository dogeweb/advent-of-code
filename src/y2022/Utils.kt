package y2022

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.time.TimedValue

/**
 * Reads lines from the given input txt file.
 */
public fun readInput(name: String) = Path("src/y2022/$name.txt").readText().trim().lines()

/**
 * Converts string to y2024.md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun <T> TimedValue<T>.println() = println("$value | $duration")
