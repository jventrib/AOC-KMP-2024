package org.jventrib.aoc.days

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.jventrib.aoc.AOCPartWindows
import org.jventrib.aoc.PartBlock
import org.jventrib.aoc.day

typealias CharStateList = List<List<CharState>>

private val result = mutableIntStateOf(0)
val offsets = getOffset()
val day04 =
    day(4) {
      val inputState: CharStateList =
          input.mapIndexed { y, list -> list.mapIndexed { x, c -> CharState(x, y, c) } }

      part1(18, 2454) {
        render { doRender(inputState) }
        exec {
          result.value = 0
          inputState
              .flatten()
              .asFlow()
              .onEach { delay(1) }
              .map { findXmas(inputState, it) }
              .collect { result.intValue += it }
          result.value
        }
      }
      part2(9, 1858) {
        render { doRender(inputState) }
        exec {
          result.value = 0

          inputState
              .flatten()
              .asFlow()
              .onEach { delay(1) }
              .map { findXmas2(inputState, it) }
              .collect { result.intValue += it }
          result.value
        }
      }
    }

@Composable
private fun PartBlock<Int>.doRender(inputState: CharStateList) {
  AOCPartWindows(result) {
    Column {
      inputState.forEach { row ->
        Row {
          row.forEach { col ->
            val size = 9
            Box(
                Modifier.size(size.dp).background(col.state.value),
            ) {
              Text(
                  col.char.toString(),
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

private fun findXmas(state: CharStateList, current: CharState): Int {
  return offsets.count { (dx, dy) ->
    if (current.state.value == Color.LightGray) current.state.value = Color.Gray
    if (current.char != 'X') return 0
    val n1 = state.getOrNull(current.y + 1 * dy)?.getOrNull(current.x + 1 * dx)
    val n2 = state.getOrNull(current.y + 2 * dy)?.getOrNull(current.x + 2 * dx)
    val n3 = state.getOrNull(current.y + 3 * dy)?.getOrNull(current.x + 3 * dx)
    val found = current.char == 'X' && n1?.char == 'M' && n2?.char == 'A' && n3?.char == 'S'
    if (found) {
      current.state.value = Color.Green
      n1?.state?.value = Color.Green
      n2?.state?.value = Color.Green
      n3?.state?.value = Color.Green
    }
    found
  }
}

private fun findXmas2(state: CharStateList, current: CharState): Int {
  if (current.state.value == Color.LightGray) current.state.value = Color.Gray
  if (current.char != 'A') return 0

  val tl = state.getOrNull(current.y - 1)?.getOrNull(current.x - 1)
  val tr = state.getOrNull(current.y - 1)?.getOrNull(current.x + 1)
  val bl = state.getOrNull(current.y + 1)?.getOrNull(current.x - 1)
  val br = state.getOrNull(current.y + 1)?.getOrNull(current.x + 1)
  val found =
      tl?.char == 'M' && br?.char == 'S' && tr?.char == 'S' && bl?.char == 'M' ||
          tl?.char == 'S' && br?.char == 'M' && tr?.char == 'M' && bl?.char == 'S' ||
          tl?.char == 'S' && br?.char == 'M' && tr?.char == 'S' && bl?.char == 'M' ||
          tl?.char == 'M' && br?.char == 'S' && tr?.char == 'M' && bl?.char == 'S'

  return if (found) {
    current.state.value = Color.Green
    br?.state?.value = Color.Green
    tr?.state?.value = Color.Green
    bl?.state?.value = Color.Green
    tl?.state?.value = Color.Green
    1
  } else 0
}

private fun getOffset(): List<Offset> {
  val dxs = listOf(-1, 0, 1)
  val dys = listOf(-1, 0, 1)
  val offsets =
      dxs.flatMap { dx ->
            dys.map { dy ->
              if (dx != 0 || dy != 0) {
                Offset(dx, dy)
              } else null
            }
          }
          .filterNotNull()
  return offsets
}

data class CharState(
    val x: Int,
    val y: Int,
    val char: Char,
    var state: MutableState<Color> = mutableStateOf(Color.LightGray)
)

data class Offset(val x: Int, val y: Int)
