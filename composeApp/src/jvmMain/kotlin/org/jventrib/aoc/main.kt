package org.jventrib.aoc

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "AOC-KMP-compose",
    ) {
        Day01()
    }
}