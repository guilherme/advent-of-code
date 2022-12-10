import java.lang.RuntimeException

fun main() {

  val PRIORITY_RANGE = concatenate(('a'.rangeTo('z'))
    .toList(), ('A'.rangeTo('Z')).toList())

  fun priority(item: Char): Int {
    val priority = PRIORITY_RANGE.indexOf(item) + 1
    if (priority == 0) {
      throw RuntimeException("Invalid item ${item}")
    } else  {
      return priority
    }
  }

  fun repeatedItems(rucksack: String): List<Char> {
    val middleIndex = rucksack.length / 2
    val firstCompartment = rucksack.substring(0, middleIndex).toCharArray()
    val secondCompartment = rucksack.substring(middleIndex , rucksack.length).toCharArray()

    return firstCompartment.filter { secondCompartment.contains(it) }.distinct()
  }

  fun part1(input: List<String>): Int {
    // find items that repeat on both compartments.
    return input.map {
      repeatedItems(it)
    }
      .flatten()
      .sumOf { priority(it) }
  }

  // badge is the one that repeats on three lines
  fun badge(elfItemGroup: List<String>): Char {
    return elfItemGroup
        // remove duplicates from same string
      .map { it.toCharArray().distinct().joinToString("") }
      .joinToString("")
      .toCharArray()
      .groupBy { it }
      .filter { it.value.size == 3 } // find the one that repeats on three lines.
      .keys
      .first()
  }

  fun part2(input: List<String>): Int {
    return input.withIndex().groupBy {
      it.index / 3
    }.map {
      badge(it.value.map { it.value })
    }
      .sumOf { priority(it) }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day03_test")
  val result1 = part1(testInput)
  check(result1 == 157)
  println("check 1 ok")

  /*  part 2 test */
  val result2 = part2(testInput)
  check(result2 == 70)
  println("check 2 ok")

  val input = readInput("Day03")
  println(part1(input))
  println(part2(input))
}