import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.funSpec
import io.kotest.matchers.shouldBe
import org.jventrib.aoc.days.day01
import org.jventrib.aoc.executeDayPart

class Day01Test :
    FunSpec({
      coroutineTestScope = true
      fun dayPartTest(day: Int, part: Int) = funSpec {
        test("day $day part $part") {
          val result = executeDayPart(day01, { part1 }, true, "example")
          result shouldBe day01.part1.expected
        }
      }
      include(dayPartTest(1, 1))
    })
