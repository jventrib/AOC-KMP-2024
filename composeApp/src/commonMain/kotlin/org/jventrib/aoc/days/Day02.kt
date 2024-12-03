package org.jventrib.aoc.days

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue
import kotlinx.coroutines.delay
import org.jventrib.aoc.day

val day02 =
    day<Int>(2) {
      val inputState = input.map { Report(it, false) }.toMutableStateList()

      part1(2, 224) {
        exec {
          val filter =
              input.filter { report ->
                val levels = report.split(" ").map { it.toInt() }
                check(levels)
              }
          filter.count()
        }
      }
      part2(4, 285) {
        render {
          Box(Modifier.fillMaxSize().padding(10.dp)) {
            val stateVertical = rememberScrollState()
            Box(Modifier.fillMaxSize().verticalScroll(stateVertical)) {
              Row {
                val cols = 8
                inputState.chunked(inputState.size / cols).forEach {
                  Column {
                    it
                        .forEach { report ->
                          Text(
                              buildAnnotatedString {
                                pushStyle(
                                    SpanStyle(
                                        background = if (report.valid) Color.Green else Color.Red,
                                    ),
                                )
                                append(report.value)
                                if (report.removedIndex != null) {
                                  val i =
                                      report.value
                                          .split(" ")
                                          .map { it.length + 1 }
                                          .take(report.removedIndex)
                                          .sum()
                                  addStyle(
                                      SpanStyle(background = Color.Yellow),
                                      i,
                                      (i + 2).coerceAtMost(report.value.length),
                                  )
                                  toAnnotatedString()
                                }
                              },
                              fontSize = 15.sp,
                          )
                        }
                  }
                }
              }
            }
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(stateVertical),
            )
            Text(
                "Count ${inputState.count { it.valid }}",
                modifier = Modifier.align(Alignment.TopEnd).padding(end = 15.dp),
            )
          }
        }
        exec {
          inputState.forEachIndexed { ri, e ->
            delay(1)
            val levels = e.value.split(" ").map { it.toInt() }
            val check =
                levels.indices.any { i ->
                  inputState[ri] = e.copy(removedIndex = i)
                  delay(10)
                  val removed = levels.filterIndexed { index, _ -> index != i }
                  check(removed)
                }
            if (check) inputState[ri] = inputState[ri].copy(valid = true)
            else inputState[ri] = inputState[ri].copy(removedIndex = null)
          }
          inputState.count { it.valid }
        }
      }
    }

private fun check(levels: List<Int>): Boolean {
  val list = levels.zipWithNext().map { (it.first - it.second).absoluteValue }
  return list.all { (1..3).contains(it) } && (list.all { it > 0 } || list.all { it < 0 })
}

data class Report(val value: String, val valid: Boolean, val removedIndex: Int? = null)
