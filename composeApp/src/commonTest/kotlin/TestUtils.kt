import io.kotest.matchers.shouldBe
import kotlin.time.measureTime
import org.jventrib.aoc.Day
import org.jventrib.aoc.lineSeparator
import org.jventrib.aoc.readInput

suspend fun <E> executeAndAssert(day: Day<E>, part: Int, example: Boolean) {
  if (example) println("Day ${day.dayNumber} - example") else println("Day ${day.dayNumber}")
  day.input = readInput(day.dayNumber, if (example) "input_example.txt" else "input.txt")
  val output: E
  val elapsed = measureTime {
    day.block(day)
    output = day.getPart(part, example).getOutput()
    println("output: $output")
  }
  println("time: ${elapsed.inWholeMilliseconds}ms")
  output shouldBe day.getPart(part, example).expected
  println("input: ${lineSeparator()}${day.input.joinToString(lineSeparator())}")
}
