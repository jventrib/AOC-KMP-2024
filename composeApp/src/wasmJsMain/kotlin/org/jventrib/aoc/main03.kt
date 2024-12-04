package org.jventrib.aoc

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jventrib.aoc.days.day03

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
  CanvasBasedWindow(canvasElementId = "ComposeTarget") { renderDayPart(day03, 2, false, "example") }
}
