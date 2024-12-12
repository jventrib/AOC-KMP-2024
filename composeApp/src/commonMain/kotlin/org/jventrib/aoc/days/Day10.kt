package org.jventrib.aoc.days

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jventrib.aoc.AOCPartWindows
import org.jventrib.aoc.Point
import org.jventrib.aoc.day

private typealias Point10 = Point<Int, PointState>


val day10 =
    day(10) {

      val inputState: List<List<Point10>> =
          input.mapIndexed { y, l ->
            l.mapIndexed { x, c ->
              Point10(
                  x,
                  y,
                  c.digitToInt(),
                  PointState(),
              )
            }
          }
      val result = mutableStateOf(0)
      part1(36, 535) {
        render {
          AOCPartWindows(result) {
            Column {
              inputState.forEach { y ->
                Row {
                  y.forEach { x ->
                    val size = 16
                    Box(
                        Modifier.size(size.dp)
                            .background(Color(x.value * 25, x.value * 25, x.value * 25)),
                    ) {
                      Text(
                          x.value.toString(),
                          modifier = Modifier.align(Alignment.Center),
                          fontSize = size.sp,
                          color = Color.Red,
                      )
                    }
                  }
                }
              }
            }


          }
        }
        exec {
          val origins = mutableMapOf<Point10, MutableSet<Point10>>()
          val queue = ArrayDeque<Point10>()
          inputState.flatten().filter { it.value == 0 }.forEach {
            queue.add(it)
            origins[it] = mutableSetOf(it)
          }
          while (!queue.isEmpty()) {
            val cur = queue.removeFirst()
            cur.neighborsIn(inputState)
                .filter { n -> n.value == cur.value + 1 }
                .forEach {
                  queue.add(it)
                  if (origins[cur] != null) {
                    origins[it]?.addAll(origins.getValue(cur)) ?: apply {
                      origins[it] = mutableSetOf()
                      origins[it]?.addAll(origins.getValue(cur))
                    }
                  }
                }
          }
          val paths = origins.filter { it.key.value == 9 }.map { it.value.size }.sum()
          paths
        }
      }
      part2(81, 1136) {
        render { }
        exec {
          val queue = ArrayDeque<Point10>()
          inputState.flatten().filter { it.value == 0 }.forEach(queue::add)
          var count = 0
          while (!queue.isEmpty()) {
            val cur = queue.removeFirst()
            cur.neighborsIn(inputState)
                .filter { n -> n.value == cur.value + 1 }
                .forEach {
                  queue.add(it)
                  if (it.value == 9) {
                    count++
                  }
                }
          }
          count
        }
      }
    }


private data class PointState(
  var prevs: MutableList<Point10> = mutableListOf(),
)
