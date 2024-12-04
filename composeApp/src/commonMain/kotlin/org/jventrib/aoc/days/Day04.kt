package org.jventrib.aoc.days

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jventrib.aoc.AOCPartWindows
import org.jventrib.aoc.day

val day04 =
    day(4) {

      val inputState = input
          .map { it.toList().toMutableStateList() }
          .toMutableStateList()

      part1(18, 0) {
        render {
          AOCPartWindows {
            inputState.forEach { row ->
              Column {
                row.forEach { col ->
                  Row(Modifier.width(20.dp)) {
                    Text(col.toString())
                  }
                }
              }
            }
          }
        }
        exec {
          0
        }
      }
      part2(0, 0) {
        render { Text("TEST1") }
        exec { 0 }
      }
    }

data class CharState<E>(val char: Char, val state: E)
