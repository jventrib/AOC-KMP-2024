package org.jventrib.aoc.days

import org.jventrib.aoc.Day
import org.jventrib.aoc.day

val day07 =
    day(7) {

      part1(3749, 66343330034722L) {
        render {
        }
        exec {
          doExec()
        }
      }
      part2(11387, 637696070419031) {
        render { }
        exec {
          doExec(true)
        }
      }
    }

private fun Day<Long>.doExec(concat: Boolean = false): Long {
  val sum = input
      .map { l ->
        l.split(':').let { segment ->
          val res = segment[0].toLong()
          val operands =
              segment[1].split(' ').filter<String> { it.isNotBlank() }
                  .map { it.trim().toLong() }
          val opeResult = ope(operands, concat)

          if (opeResult.contains(res)) res else null
        }
      }
      .filterNotNull()
      .sum()
  return sum
}

private fun ope(opes: List<Long>, concat: Boolean = false): List<Long> {
  if (opes.size == 1) return opes
  val withSum = listOf(opes[0] + opes[1]) + opes.drop(2)
  val withProduct = listOf(opes[0] * opes[1]) + opes.drop(2)
  val ints = if (concat) {
    val elements = (opes[0].toString() + opes[1].toString()).toLong()
    val withConcat = listOf(elements) + opes.drop(2)
    ope(withSum, concat) + ope(withProduct, concat) + ope(withConcat, concat)
  } else {
    ope(withSum) + ope(withProduct)
  }
  return ints
}
