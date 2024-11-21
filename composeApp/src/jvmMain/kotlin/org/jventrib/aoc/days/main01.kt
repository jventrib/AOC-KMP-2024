package org.jventrib.aoc.days

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jventrib.aoc.day

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "AOC-KMP-compose",
    ) {
        day01.part1.render()
    }
}