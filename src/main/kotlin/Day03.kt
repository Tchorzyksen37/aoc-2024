import Day03.Companion.part1
import Day03.Companion.part2

class Day03 {
    companion object {
        fun part1(input: List<String>): Long {
            return input.sumOf { str -> calculate(str) }
        }

        fun calculate(str: String): Long {
            return Regex("""mul\((?<multiplicand>\d{1,3}),(?<multiplier>\d{1,3})\)""").findAll(str)
                .map { it.groups["multiplicand"]?.value!!.toLong() * it.groups["multiplier"]?.value!!.toLong() }.sum()
        }

        fun part2(input: List<String>): Long {
            return parse("do()${input.joinToString("")}don't()")
        }

        private fun parse(str: String): Long {
            val left = str.indexOf("do()")
            val right = str.indexOf("don't()")

            if (right < left){
                return parse(str.substring(left))
            }

            try {
                if (left != -1 && right != -1) {
                    val substr = str.substring(left + 4, right)
                    return calculate(substr) + parse(str.substring(right + 7))
                }
            } catch (e: Exception) {
                println("Error: $e at $str")
            }

            return 0
        }
    }
}

fun main() {
    val testInputPart1 = listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

    val partOneTestAns = part1(testInputPart1)
    check(partOneTestAns == 161L) { "Expected 161 but got $partOneTestAns" }

    val input = readInput("day03")
    part1(input).println()

    val testInputPart2 = listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")
    val partTwoTestAns = part2(testInputPart2)
    check(partTwoTestAns == 48L) { "Expected 48 but got $partTwoTestAns" }
    part2(input).println()
}
