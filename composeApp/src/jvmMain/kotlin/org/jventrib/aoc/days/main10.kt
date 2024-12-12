package org.jventrib.aoc.days

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jventrib.aoc.renderDayPart

fun main() = application {
  val state =
      rememberWindowState(
          width = 600.dp,
          height = 400.dp,
      )
  Window(
      onCloseRequest = ::exitApplication,
      title = "AOC-KMP-compose",
      state = state,
  ) {
    renderDayPart(day10, 1, true, "example")
  }
}
