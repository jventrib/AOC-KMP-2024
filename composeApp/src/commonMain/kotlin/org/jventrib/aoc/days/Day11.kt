package org.jventrib.aoc.days

import org.jventrib.aoc.day

val day11 =
    day(11) {
      val cache: MutableMap<String, String> = mutableMapOf()

      var line = input[0]
      fun doStone(line: String): String {
        val stones = line.split(' ')
        return stones.map { stone ->
          when {
            stone == "0" -> "1"
            stone.length % 2 == 0 -> stone.substring(0, stone.length / 2)
                .run { toLong().toString() } + " " + stone.substring(stone.length / 2)
                .run { toLong().toString() }

            else -> (stone.toLong() * 2024L).toString()
          }
        }.joinToString(" ")
      }

      fun repeatStone(l: String): String {
        return cache.getOrPut(l) {
          var stone = l
          repeat(25) {
            stone = doStone(stone)
          }
          stone
        }
      }


      part1(55312, 224529) {
        render {
        }
        exec {
          repeatStone(line).split(" ").count()
        }
      }
      part2(0, 0) {
        render { }
        exec {

          //precompute the first 5000
          println("Init Cache")

          (0..5000).map {
            if (it % 100 == 0) println("Cache progress $it")
            it.toString() to repeatStone(it.toString())
          }.toMap(cache)

          println("Cache initialized")

          var count = 0

          repeatStone(line).split(" ").map {
            repeatStone(it).split(" ").map {
              println("count ${count++}")
              repeatStone(it).split(" ").count()
            }.sum()
          }.sum()
        }
      }
    }
