package org.jventrib.aoc.days

import org.jventrib.aoc.day

val day11 =
    day(11) {
      val cache: MutableMap<Pair<String, Int>, Long> = mutableMapOf()

      var line = input[0]
      fun doStone(stone: String, step: Int): Long {
        if (step == 0) return 1

        return cache.getOrPut(Pair(stone, step)) {
          when {
            stone == "0" -> doStone("1", step - 1)
            stone.length % 2 == 0 ->
              (doStone(stone.substring(0, stone.length / 2).toLong().toString(), step - 1) +
                  doStone(stone.substring(stone.length / 2).toLong().toString(), step - 1))

            else -> doStone((stone.toLong() * 2024L).toString(), step - 1)
          }
        }
      }

      part1(55312, 224529) {
        render {}
        exec {
          val sumOf = line.split(" ").sumOf { doStone(it, 25) }
          sumOf
        }
      }
      part2(65601038650482L, 266820198587914L) {
        render {}
        exec {
          val sumOf = line.split(" ").sumOf { doStone(it, 75) }
          sumOf
        }
      }
    }
