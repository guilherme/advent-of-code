import java.lang.RuntimeException

enum class RoundResult(val score: Int) {
  LOST(0),
  DRAW(3),
  WIN(6);

  companion object {
    fun fromString(input: String): RoundResult {
      // X means you need to lose,
      // Y means you need to end the round in a draw,
      // and Z means you need to win.
      return when (input) {
        "X" -> LOST
        "Y" -> DRAW
        "Z" -> WIN
        else -> throw RuntimeException("Invalid round result input: ${input}")
      }
    }
  }
}

sealed class Hand(val score: Int) {

  abstract fun versus(opponentHand: Hand): RoundResult
  abstract fun toEndInResult(resultingScore: RoundResult): Hand

  object ROCK : Hand(1) {
    override fun versus(opponentHand: Hand): RoundResult {
      return when (opponentHand) {
        PAPER -> RoundResult.LOST
        ROCK -> RoundResult.DRAW
        SCISSORS -> RoundResult.WIN
      }
    }

    override fun toEndInResult(resultingScore: RoundResult): Hand {
      return when (resultingScore) {
        RoundResult.LOST -> SCISSORS
        RoundResult.DRAW -> ROCK
        RoundResult.WIN -> PAPER
      }
    }
  }

  object PAPER : Hand(2) {
    override fun versus(opponentHand: Hand): RoundResult {
      return when (opponentHand) {
        PAPER -> RoundResult.DRAW
        ROCK -> RoundResult.WIN
        SCISSORS -> RoundResult.LOST
      }
    }
    // what is the hand I should oppose so that I can get the given result.

    override fun toEndInResult(resultingScore: RoundResult): Hand {
      return when (resultingScore) {
        RoundResult.LOST -> ROCK
        RoundResult.DRAW -> PAPER
        RoundResult.WIN -> SCISSORS
      }
    }
  }

  object SCISSORS : Hand(3) {
    override fun versus(opponentHand: Hand): RoundResult {
      return when (opponentHand) {
        PAPER -> RoundResult.WIN
        ROCK -> RoundResult.LOST
        SCISSORS -> RoundResult.DRAW
      }
    }

    override fun toEndInResult(resultingScore: RoundResult): Hand {
      return when (resultingScore) {
        RoundResult.LOST -> PAPER
        RoundResult.DRAW -> SCISSORS
        RoundResult.WIN -> ROCK
      }
    }
  }

  companion object {
    fun fromMyPlay(input: String): Hand {
      return when (input) {
        "X" -> ROCK
        // paper. rock + paper = win
        "Y" -> PAPER
        // scissosr. rock + scissors= lost
        "Z" -> SCISSORS
        else -> throw RuntimeException("Invalid hand input: ${input}")
      }
    }

    fun fromOpponentPlay(input: String): Hand {
      return when (input) {
        "A" -> ROCK
        // paper. rock + paper = win
        "B" -> PAPER
        // scissosr. rock + scissors= lost
        "C" -> SCISSORS
        else -> throw RuntimeException("Invalid opponent play input: ${input}")
      }
    }
  }
}

fun main() {
  fun scoreRound(round: String): Int {
    val (opponent, myPlay) = round.split(" ")
    val play = Hand.fromMyPlay(myPlay)
    val opponentHand = Hand.fromOpponentPlay(opponent);
    return play.versus(opponentHand).score + play.score
  }

  fun part1(input: List<String>): Int {
    return input.map {
      scoreRound(round = it)
    }.sum()
  }

  fun scoreResultingRound(round: String): Int {
    val (opponent, myResult) = round.split(" ")
    val result = RoundResult.fromString(myResult)
    val opponentHand = Hand.fromOpponentPlay(opponent)
    val myPlay = opponentHand.toEndInResult(result)
    return myPlay.score + result.score
  }

  fun part2(input: List<String>): Int {
    return input.map {
      scoreResultingRound(round = it)
    }.sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day02_test")
  val result1 = part1(testInput)
  check(result1 == 15)
  println("check 1 ok")
  val result2 = part2(testInput)
  check(result2 == 12)
  println("check 2 ok")

  val input = readInput("Day02")
  println(part1(input))
  println(part2(input))
}