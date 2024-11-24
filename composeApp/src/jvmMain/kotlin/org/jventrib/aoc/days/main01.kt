package org.jventrib.aoc.days

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jventrib.aoc.renderDayPart

fun main() = application {
  Window(
      onCloseRequest = ::exitApplication,
      title = "AOC-KMP-compose",
  ) {
    renderDayPart(day01, { part1 }, true, "example")
  }
}
