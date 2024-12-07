package org.jventrib.aoc.days

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
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

val day06 = day(6) {
  val height = input.size
  val width = input[0].length

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

    fun inArea(): Boolean {
      return this.x in 0..<width && y in 0..<height
    }
  }

  val direction = listOf('^', '>', 'v', '<')
  val guardY = input.indexOfFirst { it.any { it == '^' } }
  val guardX = input[guardY].indexOfFirst { it == '^' }
  val guard = Guard(guardX, guardY)


  val scanY = (0..<width).map { x ->
    input.mapIndexed { iy, y -> if (y[x] == '#') iy else null }
        .filterNotNull()
  }


  val scanX = input.map { y ->
    y.mapIndexed { ix, x -> if (x == '#') ix else null }
        .filterNotNull()
  }


  suspend fun doStep(
    inputState: List<List<MutableState<Char>>>,
    result: MutableIntState,
    scanx: List<List<Int>>,
    scany: List<List<Int>>
  ): Boolean {
    delay(1)




    val nextMove = guard.nextMove()
    if (inputState[nextMove.y][nextMove.x].value.let { it == '#' || it == 'O' }) {
      guard.direction = direction[(direction.indexOf(guard.direction) + 1) % 4]
    }

    val (x, y) = guard.nextMove()
    guard.x = x
    guard.y = y
    if (inputState[guard.y][guard.x].value == guard.direction) {
      return true //in loop
    }
    if (inputState[guard.y][guard.x].value == '.') {
      result.value++
    }

    inputState[guard.y][guard.x].value = guard.direction
    return false
  }

  part1(41, 5564) {
    val inputState = input.map { row -> row.map { col -> mutableStateOf(col) } }
    val result = mutableIntStateOf(1)
    render {
      doRender(result, inputState)
    }
    exec {
      try {
        while (true) {
          doStep(inputState, result, scanX, scanY)
        }
      } catch (e: IndexOutOfBoundsException) {
        println("Out of area, exiting...")
      }
      result.value
    }
  }
  part2(6, 0) {
    val result = mutableIntStateOf(1)

    val inputState = input.map { row -> row.map { col -> mutableStateOf(col) } }
    var inLoop = mutableIntStateOf(0)
    render {
      doRender(inLoop, inputState)
    }
    exec {
      println(scanY)
      println(scanX)

      inputState.forEachIndexed { iy, y ->
        y.forEachIndexed { ix, x ->
          inputState.flatten().forEachIndexed { i, c -> c.value = input.flatMap { it.toList() }[i] }

          if (x.value == '.') {
            x.value = 'O'
            println("Obstacle in ($ix, $iy)")
//          inputState[6][3].value = 'O'
            guard.x = guardX
            guard.y = guardY
            guard.direction = direction[0]

            val scanxO = scanX.mapIndexed { iys, it ->
              if (iy == iys) (it + ix).sorted() else it
            }

            val scanyO = scanY.mapIndexed { ixs, it ->
              if (ix == ixs) (it + iy).sorted() else it
            }


            try {
              while (true) {
                if (doStep(inputState, result, scanxO, scanyO)) break
              }
              println("In loop")
              inLoop.value++
            } catch (e: IndexOutOfBoundsException) {
              println("Out of area, exiting...")
            }
          }

        }

      }


      inLoop.value
    }
  }
}

@Composable
private fun PartBlock<Int>.doRender(
  result: MutableIntState,
  inputState: List<List<MutableState<Char>>>
) {
  AOCPartWindows(result) {
    Column {
      inputState.forEach { y ->
        Row {
          y.forEach { x ->
            val size = 8
            Box(
                Modifier.size(size.dp).background(
                    when (x.value) {
                      '#' -> Color.LightGray
                      '^', '>', 'v', '<' -> Color.Green
                      else -> Color.White
                    },
                ),
            ) {
              Text(
                  x.value.toString(),
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

