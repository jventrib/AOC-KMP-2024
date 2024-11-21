import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.funSpec
import io.kotest.matchers.shouldBe
import org.jventrib.aoc.days.Day01

class Day01Test : FunSpec({
    context("counter").config(coroutineTestScope = true) {
        dayPartTest(1, 1, 1, 800)
    }
})

private suspend fun dayPartTest(day: Int, part: Int, expectedExample: Int, expected: Int) = funSpec {
    test("day $day part $part should be $expected") {
        Day01().step() shouldBe expected
    }
}