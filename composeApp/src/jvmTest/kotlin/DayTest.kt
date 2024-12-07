import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.funSpec
import org.jventrib.aoc.Day
import org.jventrib.aoc.days.day07

class DayTest :
  FunSpec(
      {
//        coroutineTestScope = true
        fun <E> dayPartTest(day: Day<E>) = funSpec {
          context("Part 1") {
            test("day part 1 example") { executeAndAssert(day, 1, true) }
            test("day part 1") { executeAndAssert(day, 1, false) }
          }
          context("Part 2") {
            test("day part 2 example") { executeAndAssert(day, 2, true) }
            test("day part 2") { executeAndAssert(day, 2, false) }
          }
        }
        include(dayPartTest(day07))
      },
  )
