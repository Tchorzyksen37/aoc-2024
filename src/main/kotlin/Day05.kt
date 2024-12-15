class Day05 {
    companion object {
        fun part1(input: List<String>): Int {
            val (rules, pagesToProduceList) = splitInput(input)
            val rulesMap = parseRules(rules)

            return pagesToProduceList.map { it.split(",").map { pageNumber -> pageNumber.toInt() } }
                .filter { findFirstInvalidPageIndex(it, rulesMap) == -1 }.sumOf { getMiddlePage(it) }
        }

        fun part2(input: List<String>): Int {
            val (rules, pagesToProduceList) = splitInput(input)
            val rulesMap = parseRules(rules)

            fun fixInvalidPagesToProduce(pages: List<Int>, invalidAt: Int): List<Int> {
                var pagesWithoutInvalidOne = pages.subList(0, invalidAt) + pages.subList(invalidAt + 1, pages.size)
                val toAdd = mutableListOf(pages[invalidAt])
                while (true) {
                    val at = findFirstInvalidPageIndex(pagesWithoutInvalidOne, rulesMap)
                    if (at == -1) {
                        break
                    } else {
                        toAdd.add(pagesWithoutInvalidOne[at])
                        pagesWithoutInvalidOne = pagesWithoutInvalidOne.subList(0, at) + pagesWithoutInvalidOne.subList(at + 1, pagesWithoutInvalidOne.size)
                    }
                }
                var fix = pagesWithoutInvalidOne
                for (page in toAdd) {
                    for (rearIndex in fix.size downTo 0) {
                        val tempList = fix.toMutableList()
                        tempList.add(rearIndex, page)
                        if (findFirstInvalidPageIndex(tempList, rulesMap) == -1) {
                            fix = tempList
                            continue
                        }
                    }
                }

                return fix
            }

            return pagesToProduceList.asSequence().map { it.split(",").map { pageNumber -> pageNumber.toInt() } }
                .map { it to findFirstInvalidPageIndex(it, rulesMap) }
                .filter { it.second != -1 }
                .map { fixInvalidPagesToProduce(it.first, it.second) }
                .sumOf { getMiddlePage(it) }
        }

        private fun splitInput(input: List<String>): Pair<List<String>, List<String>> {
            val splitIndex = input.indexOf("")
            return input.subList(0, splitIndex) to input.subList(splitIndex + 1, input.size)
        }

        private fun parseRules(rules: List<String>): Map<Int, List<Int>> {
            return rules.map { rule ->
                val (key, value) = rule.split("|").map { it.toInt() }
                key to value
            }.fold(mutableMapOf()) { acc, (key, value) ->
                acc[key] = acc.getOrDefault(key, emptyList()) + value
                acc
            }
        }

        private fun findFirstInvalidPageIndex(pages: List<Int>, rulesMap: Map<Int, List<Int>>): Int {
            for ((index, page) in pages.withIndex()) {
                val succeedingPages = pages.subList(index + 1, pages.size)

                for (succeedingPage in succeedingPages) {
                    rulesMap[page].let {
                        if (it == null || !it.contains(succeedingPage)) {
                            return index
                        }
                    }
                }
            }

            return -1
        }

        private fun getMiddlePage(pages: List<Int>): Int {
            return pages[pages.size / 2]
        }
    }
}

fun main() {
    val testInput = listOf(
        "47|53",
        "97|13",
        "97|61",
        "97|47",
        "75|29",
        "61|13",
        "75|53",
        "29|13",
        "97|29",
        "53|29",
        "61|53",
        "97|53",
        "61|29",
        "47|13",
        "75|47",
        "97|75",
        "47|61",
        "75|61",
        "47|29",
        "75|13",
        "53|13",
        "",
        "75,47,61,53,29",
        "97,61,53,29,13",
        "75,29,13",
        "75,97,47,61,53",
        "61,13,29",
        "97,13,75,29,47"
    )

    val part1TestAns = Day05.part1(testInput)
    check(part1TestAns == 143) { "Test failed, got $part1TestAns" }

    val input = readInput("day05")
    Day05.part1(input).println()

    val part2TestAns = Day05.part2(testInput)
    check(part2TestAns == 123) { "Test failed, got $part2TestAns" }
    Day05.part2(input).println()

}