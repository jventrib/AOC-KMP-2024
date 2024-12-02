package org.jventrib.aoc.days

import kotlin.math.absoluteValue
import org.jventrib.aoc.day

val day02 =
    day<Int>(2) {
      part1(2, 224) {
        exec {
          val filter =
              input.filter { report ->
                val levels = report.split(" ").map { it.toInt() }
                val check1 = levels == levels.sorted() || levels == levels.sortedDescending()
                val check2 =
                    levels.windowed(2).all { duo ->
                      (1..3).contains((duo[1] - duo[0]).absoluteValue)
                    }
                check1 && check2
              }
          filter.count()
        }
      }
      part2(31, 23082277) { exec { 0 } }
    }
