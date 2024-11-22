package org.jventrib.aoc.days

import androidx.compose.foundation.Canvas
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import org.jventrib.aoc.day

val day01 = day<Int>(1) {

    part1(800, 800) {
        var mutableCount = mutableIntStateOf(0)
        render {
            val state by remember { mutableCount }
            val size = state * 10f
            Canvas(Modifier.Companion) {
                drawRect(color = Color.Companion.Red, size = Size(size, size))
            }
            Text("count is ${state}")
        }

        exec {
            (1..800).forEach {
                delay(10)
                mutableCount.value = it
            }
            mutableCount.value
        }
    }
}

