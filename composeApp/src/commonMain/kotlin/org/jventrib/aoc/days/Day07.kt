package org.jventrib.aoc.days

import org.jventrib.aoc.day

val day07 =
    day(7) {

      part1(3749, 0) {
        render {
        }
        exec {
          val list = input.map {l ->
            l.split(':').let {
              val res = it[0]
              val operands = it[1].split(" ").map { it.trim().toInt() }

              ope(operands)

              res to operands
            }
          }.toMap()
          0
        }
      }
      part2(0, 0) {
        render { }
        exec {
          0
        }
      }
    }
