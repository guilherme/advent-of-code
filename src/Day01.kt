fun main() {
  fun elfScores(input: List<String>): List<Int> = input
    .split { it.isBlank() }
    .map { it.sumOf { it.toInt() } }

  fun part1(input: List<String>): Int {
    return elfScores(input).max()
  }

  fun part2(input: List<String>): Int {
    return elfScores(input)
      .sorted()
      .takeLast(3)
      .sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day01_test")
  check(part1(testInput) == 24000)
  println("check 1 ok")
  check(part2(testInput) == 45000)
  println("check 2 ok")

  val input = readInput("Day01")
  println(part1(input))

  println(part2(input))
}
