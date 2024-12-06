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
import kotlinx.coroutines.delay
import org.jventrib.aoc.day

val day06 = day(6) {
  val inputState = input.map { row -> row.map { col -> mutableStateOf(col) } }
  val height = inputState.size
  val width = inputState[0].size
  val guardY = inputState.indexOfFirst { it.any { it.value == '^' } }
  val guardX = inputState[guardY].indexOfFirst { it.value == '^' }
  val guard = Guard(guardX, guardY)
  val direction = listOf('^', '>', 'v', '<')

  part1(0, 0) {
    render {
//      Row {
//        inputState.forEach { y ->
//          Column {
//            y.forEach { x ->
//              val size = 15
//              Box(
//                  Modifier.size(size.dp).background(
//                      when (x.value) {
//                        '#' -> Color.LightGray
//                        '^' -> Color.Yellow
//                        else -> Color.White
//                      },
//                  ),
//              ) {
//                Text(
//                    x.value.toString(),
//                    modifier = Modifier.align(Alignment.Center),
//                    fontSize = size.sp,
//                )
//              }
//            }
//          }
//        }
//      }
    }
    exec {
      while (true) {
        delay(1000)
        val nextMove = guard.nextMove()
        if (inputState[nextMove.y][nextMove.x].value == '#') {
          guard.direction = direction[(direction.indexOf(guard.direction) + 1) % 4]
        } else {
          guard.x = nextMove.x
          guard.y = nextMove.y
        }
        inputState[guard.y][guard.x].value = guard.direction
      }
      0
    }
  }
  part2(0, 0) {
    render {}
    exec { 0 }
  }
}

data class Guard(var x: Int, var y: Int, var direction: Char = '^') {
  fun nextMove(): Guard {
    return when (direction) {
      '^' -> this.copy(y = y - 1)
      'v' -> this.copy(y = y + 1)
      '<' -> this.copy(x = x - 1)
      '>' -> this.copy(x = x + 1)
      else -> throw Exception("Unknown direction")
    }
  }
}
