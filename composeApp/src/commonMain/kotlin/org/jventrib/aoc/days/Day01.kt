package org.jventrib.aoc.days

import kotlin.math.absoluteValue
import org.jventrib.aoc.day

val day01 =
    day<Int>(1) {
      val lists = input.map { it.split("   ") }
      val left = lists.map { it.first() }.map { it.toInt() }
      val right = lists.map { it.last() }.map { it.toInt() }
      part1(11, 2375403) {
        exec { left.sorted().zip(right.sorted()) { l, r -> (r - l).absoluteValue }.sum() }
      }
      part2(31, 23082277) { exec { left.sumOf { l -> right.count { r -> l == r } * l } } }
    }
