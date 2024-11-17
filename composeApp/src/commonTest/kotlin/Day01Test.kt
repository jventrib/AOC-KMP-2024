import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.jventrib.aoc.Day01

class Day01Test : FunSpec({
    context("counter") {
        test("should be 200") {
            Day01() shouldBe 200
        }
    }
})