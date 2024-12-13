import Day04.Companion.part2

class Day04 {

    companion object {

        private fun List<String>.readLetterAt(pos: Pair<Int, Int>): Char? {
            if (pos.second !in indices || pos.first !in 0 until first().length) {
                return null
            }

            return this[pos.first][pos.second]
        }

        fun part1(input: List<String>): Int {
            var count = 0
            for (verticalIndex in input.indices) {
                for (horizontalIndex in 0 until input.first().length) {
                    val currentChar = input[horizontalIndex][verticalIndex]

                    if (currentChar == 'X' || currentChar == 'S') {
                        count += lists(horizontalIndex, verticalIndex)
                            .map { innerList -> innerList.map { input.readLetterAt(it) } }
                            .filterNot { innerList -> innerList.contains(null) }
                            .map { it.joinToString("") }
                            .count { it == "XMAS" }
                    }

                }
            }

            return count
        }

        private fun lists(
            horizontalIndex: Int,
            verticalIndex: Int
        ) = listOf(
            listOf(
                Pair(horizontalIndex, verticalIndex), Pair(horizontalIndex + 1, verticalIndex),
                Pair(horizontalIndex + 2, verticalIndex), Pair(horizontalIndex + 3, verticalIndex)
            ),
            listOf(
                Pair(horizontalIndex, verticalIndex), Pair(horizontalIndex - 1, verticalIndex),
                Pair(horizontalIndex - 2, verticalIndex), Pair(horizontalIndex - 3, verticalIndex)
            ),
            listOf(
                Pair(horizontalIndex, verticalIndex), Pair(horizontalIndex, verticalIndex + 1),
                Pair(horizontalIndex, verticalIndex + 2), Pair(horizontalIndex, verticalIndex + 3)
            ),
            listOf(
                Pair(horizontalIndex, verticalIndex), Pair(horizontalIndex, verticalIndex - 1),
                Pair(horizontalIndex, verticalIndex - 2), Pair(horizontalIndex, verticalIndex - 3)
            ),
            listOf(
                Pair(horizontalIndex, verticalIndex), Pair(horizontalIndex + 1, verticalIndex + 1),
                Pair(horizontalIndex + 2, verticalIndex + 2), Pair(horizontalIndex + 3, verticalIndex + 3)
            ),
            listOf(
                Pair(horizontalIndex, verticalIndex), Pair(horizontalIndex - 1, verticalIndex - 1),
                Pair(horizontalIndex - 2, verticalIndex - 2), Pair(horizontalIndex - 3, verticalIndex - 3)
            ),
            listOf(
                Pair(horizontalIndex, verticalIndex), Pair(horizontalIndex + 1, verticalIndex - 1),
                Pair(horizontalIndex + 2, verticalIndex - 2), Pair(horizontalIndex + 3, verticalIndex - 3)
            ),
            listOf(
                Pair(horizontalIndex, verticalIndex), Pair(horizontalIndex - 1, verticalIndex + 1),
                Pair(horizontalIndex - 2, verticalIndex + 2), Pair(horizontalIndex - 3, verticalIndex + 3)
            )
        )

        fun part2(input: List<String>): Int {
            var count = 0
            for (verticalIndex in input.indices) {
                for (horizontalIndex in 0 until input.first().length) {
                    val currentChar = input[horizontalIndex][verticalIndex]

                    if (currentChar == 'A') {
                        count += if (listOf(
                                listOf(
                                    Pair(horizontalIndex - 1, verticalIndex - 1),
                                    Pair(horizontalIndex, verticalIndex),
                                    Pair(horizontalIndex + 1, verticalIndex + 1)
                                ),
                                listOf(
                                    Pair(horizontalIndex - 1, verticalIndex + 1),
                                    Pair(horizontalIndex, verticalIndex),
                                    Pair(horizontalIndex + 1, verticalIndex - 1)
                                ),
                            )
                                .map { innerList -> innerList.map { input.readLetterAt(it) } }
                                .filterNot { innerList -> innerList.contains(null) }
                                .map { it.joinToString("") }
                                .count { it == "MAS" || it == "SAM" } == 2
                        ) 1 else 0
                    }

                }
            }

            return count
        }
    }

}

fun main() {
    val testInput = listOf(
        "MMMSXXMASM", "MSAMXMSMSA", "AMXSXMAAMM", "MSAMASMSMX", "XMASAMXAMM", "XXAMMXXAMA",
        "SMSMSASXSS", "SAXAMASAAA", "MAMMMXMMMM", "MXMXAXMASX"
    )

    val part1TestAns = Day04.part1(testInput)
    check(part1TestAns == 18) { "Test failed, got $part1TestAns" }

    val input = readInput("day04")
    Day04.part1(input).println()

    val partTwoTestAns = part2(testInput)
    check(partTwoTestAns == 9) { "Expected 9 but got $partTwoTestAns" }
    part2(input).println()
}