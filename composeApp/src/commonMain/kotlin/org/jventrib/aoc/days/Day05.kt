package org.jventrib.aoc.days

import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import org.jventrib.aoc.AOCPartWindows
import org.jventrib.aoc.day

val day05 =
    day(5) {
      val result = mutableStateOf(0)
      val after = input.takeWhile { it.isNotBlank() }.map { it.split('|') }
          .map { it[0] to it[1] }
          .groupBy { it.first.toInt() }
          .mapValues { it.value.map { it.second.toInt() } }

      val updates =
          input.dropWhile { it.isNotBlank() }.drop(1).map { it.split(',').map { it.toInt() } }
              .asFlow().onStart { delay(3000) }

      val comparator: (Int, Int) -> Int = { a, b -> if (after[a]?.contains(b) == true) -1 else 0 }
        lateinit var fl: Flow<Int>

      part1(143, 5268) {
        render {
          AOCPartWindows(fl.collectAsState(0)) {
            Text("Launched")

          }
        }
        exec {
          fl = updates
              .onEach { delay(1000) }
              .filter { update -> update == update.sortedWith(comparator) }
              .map { it[it.size / 2] }


          result.value
        }
      }
      part2(123, 5799) {
        render { }
        exec {
          val validUpdates = updates.filter { update ->
            update != update.sortedWith(comparator)
          }.map { it.sortedWith(comparator) }
          validUpdates.map { it[it.size / 2] }.collect {
            result.value += it
          }
          result.value
        }
      }
    }
