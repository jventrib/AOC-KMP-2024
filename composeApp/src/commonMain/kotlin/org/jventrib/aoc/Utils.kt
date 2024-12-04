package org.jventrib.aoc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/** Reads lines from the given input txt file. */
fun readInput(day: Int, name: String = "input.txt") =
    readAllText("../input/${day.toString().padStart(2, '0')}/$name")

expect fun readAllText(filePath: String): List<String>

fun List<String>.parseLineToIntList() = first().split(",").map(String::toInt)

fun String.isNumeric() = this.toIntOrNull() != null

fun String.replaceLast(oldValue: String, newValue: String, ignoreCase: Boolean = false): String {
  val index = lastIndexOf(oldValue, ignoreCase = ignoreCase)
  return if (index < 0) this else this.replaceRange(index, index + oldValue.length, newValue)
}

@Composable
fun <E> renderDayPart(d: Day<E>, part: Int, example: Boolean, label: String) {
  d.scope = rememberCoroutineScope()
  println("Day ${d.dayNumber} - $label")

  d.input = readInput(d.dayNumber, if (example) "input_example.txt" else "input.txt")
  println("input: ${lineSeparator()}${d.input.joinToString(lineSeparator())}")
  d.block(d)
  val output = d.getPart(part, example).render()

  println("output: $output")
}

fun <E> day(dayNumber: Int, block: Day<E>.() -> Unit): Day<E> = Day(dayNumber, block)

class Day<E>(val dayNumber: Int, val block: Day<E>.() -> Unit) {
  lateinit var input: List<String>
  lateinit var part1: Part<E>
  lateinit var part1Example: Part<E>
  lateinit var part2: Part<E>
  lateinit var part2Example: Part<E>
  lateinit var scope: CoroutineScope

  fun part1(
      expectedExampleOutput: E,
      expectedOutput: E? = null,
      block: PartBlock<E>.() -> Unit
  ): Part<E> {
    part1 = Part(scope, expectedOutput, block)
    part1Example = Part(scope, expectedExampleOutput, block)
    return part1
  }

  fun part2(
      expectedExampleOutput: E,
      expectedOutput: E? = null,
      block: PartBlock<E>.() -> Unit
  ): Part<E> {
    part2 = Part(scope, expectedOutput, block)
    part2Example = Part(scope, expectedExampleOutput, block)
    return part2
  }

  fun getPart(part: Int, example: Boolean): Part<E> =
      when {
        part == 1 && example -> part1Example
        part == 1 && !example -> part1
        part == 2 && example -> part2Example
        part == 2 && !example -> part2
        else -> throw Exception("Invalid day / part")
      }
}

class Part<E>(
    var scope: CoroutineScope,
    val expected: E?,
    private val block: PartBlock<E>.() -> Unit,
) {
  private val partBlock = PartBlock<E>()

  suspend fun getOutput(): E {
    block(partBlock)
    return partBlock.execBlock()
  }

  @Composable
  fun render() {
    block(partBlock)
    partBlock.renderBlock()
    scope.launch { partBlock.execBlock() }
  }
}

class PartBlock<E>() {
  lateinit var renderBlock: @Composable () -> Unit
  lateinit var execBlock: suspend () -> E

  fun render(block: @Composable () -> Unit) {
    renderBlock = block
  }

  fun exec(block: suspend () -> E) {
    execBlock = block
  }
}

data class Point(var x: Int, var y: Int, var value: Int = 0) {

  fun moveX(amount: Int) {
    x += amount
  }

  fun moveY(amount: Int) {
    y += amount
  }

  fun neighborsIn(points: List<List<Point>>) =
      listOfNotNull(
          points[y].getOrNull(x - 1),
          points[y].getOrNull(x + 1),
          points.getOrNull(y - 1)?.get(x),
          points.getOrNull(y + 1)?.get(x),
      )
}

fun <T, R> Flow<T>.concurrentMap(
    dispatcher: CoroutineDispatcher,
    concurrencyLevel: Int,
    transform: suspend (T) -> R
): Flow<R> {
  return flatMapMerge(concurrencyLevel) { value -> flow { emit(transform(value)) } }
      .flowOn(dispatcher)
}

fun lineSeparator() = "\n"

// Common
expect fun getMillis(): Long

inline fun <T> Iterable<T>.takeWhileInclusive(predicate: (T) -> Boolean): List<T> {
  var shouldContinue = true
  return takeWhile {
    val result = shouldContinue
    shouldContinue = predicate(it)
    result
  }
}
