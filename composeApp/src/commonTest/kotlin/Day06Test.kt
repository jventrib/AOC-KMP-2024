import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.funSpec
import org.jventrib.aoc.Day
import org.jventrib.aoc.days.day05
import org.jventrib.aoc.days.day06

class Day06Test :
    FunSpec({
      coroutineTestScope = true
      fun <E> dayPartTest(day: Day<E>, part: Int) = funSpec {
        context("Day ${day.dayNumber} Part $part") {
          test("day ${day.dayNumber} part $part example") { executeAndAssert(day, part, true) }
          test("day ${day.dayNumber} part $part") { executeAndAssert(day, part, false) }
        }
      }
      include(dayPartTest(day06, 1))
      include(dayPartTest(day06, 2))
    })
