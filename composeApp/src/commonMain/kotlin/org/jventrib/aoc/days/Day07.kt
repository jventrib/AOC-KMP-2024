package org.jventrib.aoc.days

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import kotlinx.coroutines.delay
import org.jventrib.aoc.AOCPartWindows
import org.jventrib.aoc.Day
import org.jventrib.aoc.day


val day07 =
    day(7) {

      val state = input
          .map { l ->
            l.split(':').let { segment ->
              val res = segment[0].toLong()
              val operands =
                  segment[1].split(' ').filter<String> { it.isNotBlank() }
                      .map { it.trim().toLong() }
              Equation(res, operands)
            }
          }.toMutableStateList()

      val result = mutableLongStateOf(0)
      part1(3749, 66343330034722L) {
        render {
        }
        exec {
          doExec(state)
        }
      }
      part2(11387, 637696070419031) {
        render {
          AOCPartWindows(result) {
            Column {
              state.forEach { eq ->
                Text(
                    buildAnnotatedString {
                      if (eq.status == true) {
                        pushStyle(SpanStyle(background = Color.Green))
                      }
                      append(eq.result.toString())
                      if (eq.status == true) {
                        pop()
                      }

                      toAnnotatedString()
                    },
                )
              }
            }
          }
        }
        exec {
          doExec(state, true)
        }
      }
    }

private suspend fun Day<Long>.doExec(
  state: List<Equation>,
  concat: Boolean = false
): Long {
  val sum = state
      .map { eq ->
        delay(300)
        val opeResult = ope(eq, concat)
        if (opeResult.map { it.result }.contains(eq.result)) {
          eq.status = true
          eq.result
        } else null
      }
      .filterNotNull()
      .sum()
  return sum
}

private fun ope(eq: Equation, concat: Boolean = false): List<Equation> {
  if (eq.workingOperands.size == 1) {
    return listOf(eq)
  }

  val withSum = eq.doOperation(Equation.OperatorType.SUM)
  val withProduct = eq.doOperation(Equation.OperatorType.PRODUCT)
  val ints = if (concat) {
    val withConcat = eq.doOperation(Equation.OperatorType.CONCAT)
    ope(withSum, concat) + ope(withProduct, concat) + ope(withConcat, concat)
  } else {
    ope(withSum) + ope(withProduct)
  }
  return ints
}

private data class Equation(
  val result: Long,
  val operands: List<Long>,
  val type: OperatorType? = null,
  var workingOperands: List<Long> = operands
) {
  enum class OperatorType(val symbol: String, val fn: (Long, Long) -> Long) {
    SUM("+", { a, b -> a + b }),
    PRODUCT("*", { a, b -> a * b }),
    CONCAT("||", { a, b -> (a.toString() + b.toString()).toLong() })
  }

  data class Operator(val types: OperatorType, val status: Boolean?)

  val operators: List<Operator> = mutableStateListOf<Operator>()
  var status by mutableStateOf<Boolean?>(null)

  fun doOperation(op: OperatorType): Equation {
    val list = listOf(
        op.fn.invoke(
            workingOperands[0],
            workingOperands[1],
        ),
    ) + workingOperands.drop(2)
    if (list.size == 1) {
      return copy(
          workingOperands = list,
          result = list[0],
      )

    } else {
      return copy(
          workingOperands = list,
      )

    }
  }
}
