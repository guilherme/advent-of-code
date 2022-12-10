import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/* split array into groups  by predicate */
fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> =
    fold(mutableListOf(mutableListOf<T>())) { acc, t ->
        if (predicate(t)) acc.add(mutableListOf())
        else acc.last().add(t)
        acc
    }.filterNot { it.isEmpty() }

fun <T> concatenate(vararg lists: List<T>): List<T> {
    return listOf(*lists).flatten()
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
