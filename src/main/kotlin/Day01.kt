import kotlin.math.abs

fun main() {

    val testInput = listOf("3   4", "4   3", "2   5", "1   3", "3   9", "3   3")

    fun MutableList<Int>.binaryAdd(num: Int) {
        val index = this.binarySearch(num)
        if (index < 0)
            this.add(index.inv(), num)
        else
            this.add(index, num)
    }

    fun part1(input: List<String>): Int {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        for (i in input) {
            val inputSplit = i.split(" ").filter { it.isNotBlank() }

            inputSplit[0].trim().toInt().let { leftList.binaryAdd(it) }
            inputSplit[1].trim().toInt().let { rightList.binaryAdd(it) }
        }

        return leftList.zip(rightList).sumOf { (left, right) -> abs(left - right) }

    }

    fun oneGoldStar(input: List<String>): Int {
        val (left, right) = input.map { line ->
            val first = line.substringBefore(" ").toInt()
            val second = line.substringAfterLast(" ").toInt()
            first to second
        }.unzip()

        return left.sorted().zip(right.sorted()).sumOf { (left, right) -> abs(left - right) }
    }

    fun part2(input: List<String>): Int {
        val leftList = mutableListOf<Int>()
        val rightMap = mutableMapOf<Int, Int>()

        for (i in input) {
            val inputSplit = i.split(" ").filter { it.isNotBlank() }

            inputSplit[0].trim().toInt().let { leftList.binaryAdd(it) }
            inputSplit[1].trim().toInt().let {
                if (rightMap.containsKey(it))
                    rightMap[it] = rightMap[it]!! + 1
                else
                    rightMap.put(it, 1)
            }
        }

        return leftList.sumOf { left -> rightMap.getOrDefault(left, 0) * left }
    }

    fun twoGoldStars(input: List<String>): Int {
        val (left, right) = input.map { line -> line.split(Regex("\\s+")).let { it[0].toInt() to it[1].toInt() } }
            .unzip()

        val frequencies = right.groupingBy { it }.eachCount()
        return left.fold(0) { acc, num -> acc + frequencies.getOrDefault(num, 0) * num }
    }

    check(oneGoldStar(testInput) == 11)

    val input = readInput("day01")
    oneGoldStar(input).println()

    check(twoGoldStars(testInput) == 31)
    twoGoldStars(input).println()
}
