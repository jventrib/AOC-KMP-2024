package org.jventrib.aoc.days

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.jventrib.aoc.AOCPartWindows
import org.jventrib.aoc.PartBlock
import org.jventrib.aoc.day

val day08 =
    day(8) {

      val inputState = input.map { l -> l.map { mutableStateOf(it) } }
      val result = mutableStateOf(0)
      val height = input.size
      val width = input[0].length

      fun getCouples(): List<List<Pair<Point, Point>>> {
        return inputState.mapIndexed<List<MutableState<Char>>, List<Point?>> { iy, y ->
          y.mapIndexed<MutableState<Char>, Point?> { ix, it ->
            if (it.value != '.' && it.value != '#') {
              Point(ix, iy, it.value)
            } else null
          }
        }.flatten<Point?>().filterNotNull<Point>()
            .groupBy<Point, Char> { it.code }.values.map<List<Point>, List<Pair<Point, Point>>> { codeAntennas ->
              codeAntennas.map<Point, List<Pair<Point, Point>>> { a1 ->
                codeAntennas.map<Point, Pair<Point, Point>?> { a2 ->
                  if (a1 != a2) a1 to a2 else null
                }.filterNotNull<Pair<Point, Point>>()
              }.flatten<Pair<Point, Point>>()
            }
      }

      @Composable
      fun PartBlock<Int>.doRender() {
        AOCPartWindows(result) {
          Column {
            inputState.forEach { y ->
              Row {
                y.forEach {
                  val size = 16
                  Box(
                      Modifier.size(size.dp).background(
                          when (it.value) {
                            '#' -> Color.Green
                            else -> Color.White
                          },
                      ),
                  ) {
                    Text(
                        it.value.toString(),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = size.sp,
                    )
                  }
                }
              }
            }
          }
        }
      }

      part1(14, 269) {
        render { doRender() }
        exec {
          getCouples().forEach { code ->
            code.forEach { (a1, a2) ->
              val v = a2 - a1
              val antinode = (a2 + v)
              delay(1)
              val point = inputState.getOrNull(antinode.y)?.getOrNull(antinode.x)
              if (point != null && point.value != '#') {
                result.value++
                point.value = '#'
              }
            }
          }
          result.value
        }
      }
      part2(34, 949) {
        render { doRender() }
        exec {
          getCouples().forEach { code ->
            code.forEach { (a1, a2) ->
              val v = a2 - a1

              var times = 1
              while (true) {
                val antinode = (a2 + (v * times))
                if (antinode.x !in (0..<width) || antinode.y !in (0..<height)) {
                  break
                }


                delay(10)
                val point = inputState.getOrNull(antinode.y)?.getOrNull(antinode.x)
                if (point != null && point.value != '#') {
                  point.value = '#'
                }

                result.value = inputState.flatten().count { it.value != '.' }
                times++
              }
            }
          }
          result.value
        }
      }
    }

private data class Point(val x: Int, val y: Int, val code: Char) {
  operator fun minus(other: Point): Point {
    return Point(x - other.x, y - other.y, code)
  }

  operator fun plus(other: Point): Point {
    return Point(x + other.x, y + other.y, code)
  }

  operator fun times(times: Int): Point {
    return Point(x * times, y * times, code)

  }
}
