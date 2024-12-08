package org.jventrib.aoc.days

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jventrib.aoc.renderDayPart

fun main() = application {
  val state =
      rememberWindowState(
          width = 800.dp,
          height = 600.dp,
      )
  Window(
      onCloseRequest = ::exitApplication,
      title = "AOC-KMP-compose",
      state = state,
  ) {
    renderDayPart(day07, 2, true, "example")
  }
}
