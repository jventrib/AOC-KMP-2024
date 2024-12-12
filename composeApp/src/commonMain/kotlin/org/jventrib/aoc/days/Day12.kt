package org.jventrib.aoc.days

import androidx.compose.runtime.mutableStateOf
import org.jventrib.aoc.Point
import org.jventrib.aoc.day

typealias Point12 = Point<Char, Int>
typealias Point12List = List<List<Point12>>

val day12 =
    day(12) {

      val inputState: Point12List =
          input.mapIndexed { y, l ->
            l.mapIndexed { x, c ->
              Point(x, y, c, 0)
            }
          }
      val result = mutableStateOf(0)

      fun findArea(point: Point12, area: MutableList<Point12>) {
        area += point
        val neighbors = point.neighborsIn(inputState)
            .filter { it.value == point.value }
        neighbors.forEach {
          if (!area.contains(it)) {
            findArea(it, area)
          }
        }
      }

      part1(1930, 0) {
        render {
        }
        exec {

          val regions = inputState.flatten().fold(listOf<Region>()) { acc, p ->
            if (acc.flatMap { it.plants }.none { it == p }) {
              acc + Region(p.value, mutableListOf<Point12>().let { findArea(p, it); it })
            } else acc
          }.map { it.name to it.plants.size }
          0
        }
      }
      part2(0, 0) {
        render { }
        exec {
          0
        }
      }
    }

data class Region(val name: Char, val plants: List<Point12>)
