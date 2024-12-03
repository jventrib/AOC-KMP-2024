package org.jventrib.aoc.days

import androidx.compose.runtime.toMutableStateList
import org.jventrib.aoc.day

val day03 =
    day<Int>(3) {
      val inputState = input.joinToString()
          .map { CharStatus(it, false) }.toMutableStateList()

      part1(2, 224) { exec {
        0 } }
      part2(4, 285) { exec { 0 } }
    }

data class CharStatus(val char: Char, val valid: Boolean)
