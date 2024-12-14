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

      fun findArea(
        point: Point12, area: MutableList<Point12>, grid: Point12List, checkDiag: Boolean = false
      ) {
        area += point
        val neighbors = point.neighborsIn(grid)
            .filter { it.value == point.value }
        point.state = 4 - neighbors.take(4).size

        if (checkDiag) {
          val neighbors8 = point.neighbors8In(grid)
              .filter { it.value == point.value }
          if (neighbors8.size == 7) {
            point.state = -1
          }
        }
        neighbors.forEach {
          if (!area.contains(it)) {
            findArea(it, area, grid, checkDiag)
          }
        }
      }

      fun double(inputState1: Point12List): Point12List {
        val doubledInputState: Point12List = List(inputState1.size * 2) { y ->
          List(inputState1[0].size * 2) { x ->
            val originalPoint = inputState1[y / 2][x / 2]
            Point(x, y, originalPoint.value, originalPoint.state)
          }
        }
        return doubledInputState
      }

      part1(1930, 1522850) {
        render {
        }
        exec {
          val regions = inputState.flatten().fold(listOf<Region>()) { acc, p ->
            if (acc.flatMap { it.plants }.none { it == p }) {
              acc + Region(
                  p.value,
                  mutableListOf<Point12>().let { findArea(p, it, inputState); it },
              )
            } else acc
          }

          regions.sumOf { it -> it.getArea() * it.getPerimeter() }
        }
      }
      part2(1206, 953738) {
        render { }
        exec {
          //double the matrix size
          val doubled = double(inputState)

          val regions = doubled.flatten().fold(listOf<Region>()) { acc, p ->
            if (acc.flatMap { it.plants }.none { it == p }) {
              acc + Region(
                  p.value,
                  mutableListOf<Point12>().let { findArea(p, it, doubled, true); it },
              )
            } else acc
          }

          regions
//              .onEach { println("${it.name}, ${it.plants[0].let { "(${it.x / 2},${it.y / 2})" }} -> ${it.getSides()}") }
              .sumOf { it -> it.getArea() * it.getSides() } / 4
        }
      }
    }

data class Region(val name: Char, val plants: List<Point12>) {

  fun getArea(): Int = plants.size

  fun getPerimeter(): Int = plants.sumOf { it.state }

  fun getSides(): Int {
    return plants.count { it.state == 2 || it.state == -1 }
  }
}
