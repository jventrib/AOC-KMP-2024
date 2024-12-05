package org.jventrib.aoc.days

import org.jventrib.aoc.day

val day05 =
    day(5) {
      val after = input.takeWhile { it.isNotBlank() }.map { it.split('|') }
          .map { it[0] to it[1] }
          .groupBy { it.first.toInt() }
          .mapValues { it.value.map { it.second.toInt() } }

      val updates =
          input.dropWhile { it.isNotBlank() }.drop(1).map { it.split(',').map { it.toInt() } }

      val comparator: (Int, Int) -> Int = { a, b -> if (after[a]?.contains(b) == true) -1 else 0 }

      part1(143, 5268) {
        render {
        }
        exec {
          val validUpdates = updates.filter { update ->
            update == update.sortedWith(comparator)
          }
          validUpdates.sumOf { it[it.size / 2] }
        }
      }
      part2(123, 5799) {
        render { }
        exec {
          val validUpdates = updates.filter { update ->
            update != update.sortedWith(comparator)
          }.map { it.sortedWith(comparator) }
          validUpdates.sumOf { it[it.size / 2] }
        }
      }
    }
