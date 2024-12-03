import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.funSpec
import org.jventrib.aoc.Day
import org.jventrib.aoc.days.day03

class Day03Test :
    FunSpec({
      coroutineTestScope = true
      fun <E> dayPartTest(day: Day<E>, part: Int) = funSpec {
        context("Day ${day.dayNumber} Part $part") {
          test("day ${day.dayNumber} part $part example") { executeAndAssert(day, part, true) }
          test("day ${day.dayNumber} part $part") { executeAndAssert(day, part, false) }
        }
      }
      include(dayPartTest(day03, 1))
      include(dayPartTest(day03, 2))
    })
