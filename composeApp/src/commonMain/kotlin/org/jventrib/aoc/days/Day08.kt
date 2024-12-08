package org.jventrib.aoc.days

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.jventrib.aoc.AOCPartWindows
import org.jventrib.aoc.day

val day08 =
    day(8) {

      val inputState = input.map { l -> l.map { mutableStateOf(it) } }
      val result = mutableStateOf(0)
      part1(14, 0) {
        render {
          AOCPartWindows(result) {
            Column {
              inputState.forEach { y ->
                Row {
                  y.forEach {
                    val size = 16
                    Box(Modifier.size(size.dp)) {
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
        exec {
          val antennas = inputState.mapIndexed { iy, y ->
            y.mapIndexed { ix, it ->
              if (it.value != '.' && it.value != '#') {
                Point(ix, iy, it.value)
              } else null
            }
          }.flatten().filterNotNull().groupBy { it.code }


          val couples = antennas.values.map { codeAntennas ->
            codeAntennas.map { a1 ->
              codeAntennas.map { a2 ->
                if (a1 != a2) a1 to a2 else null
              }.filterNotNull()
            }.flatten()
          }


          couples.forEach { code ->
            code.forEach { (a1, a2) ->
              val v = a2 - a1
              val antinode = (a1 - v)
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
      part2(0, 0) {
        render { }
        exec {
          0
        }
      }
    }

private data class Point(val x: Int, val y: Int, val code: Char) {
  operator fun minus(other: Point): Point {
    return Point(x - other.x, y - other.y, code)
  }
}
