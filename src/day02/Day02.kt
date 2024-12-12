package day02

import println
import readInput
import kotlin.math.abs

fun main() {

    fun oneGoldStar(input: List<String>): Int {
        val listOfNumbers = input.map { it -> it.split(Regex("\\s+")).map { num -> num.toInt() } }

        var count = 0

        for (numbers in listOfNumbers) {
            var status = "SAFE"
            val order = numbers[1] - numbers[0]
            val ordering = when {
                order > 0 -> "ascending"
                order < 0 -> "descending"
                else -> "equal"
            }
            if (ordering == "equal") {
                status = "UNSAFE"
            }

            for (number in 1 until numbers.size) {
                if (ordering == "ascending" && (numbers[number] - numbers[number - 1]) in (1..3).filter { it != 0 }) {
                    continue
                } else if (ordering == "descending" && (numbers[number - 1] - numbers[number]) in (1..3).filter { it != 0 }) {
                    continue
                } else {
                    status = "UNSAFE"
                    break
                }
            }

            if (status == "SAFE") {
                count++
            }

        }

        return count
    }

    fun isSafe(numbers: List<Int>): Boolean {
        var initMonotone: Int? = null
        for (number in 1 until numbers.size) {
            val monotone =
                if (numbers[number] - numbers[number - 1] == 0) 0 else (numbers[number] - numbers[number - 1]) / abs(
                    numbers[number] - numbers[number - 1]
                )
            if (initMonotone == null) {
                initMonotone = monotone
            }
            if (initMonotone != monotone) {
                return false
            }
            if (monotone * (numbers[number] - numbers[number - 1]) !in (1..3).filter { it != 0 }) {
                return false
            }
        }

        return true
    }

    fun twoGoldStars(input: List<String>): Int {
        val listOfNumbers = input.map { it -> it.split(Regex("\\s+")).map { num -> num.toInt() }.toMutableList() }

        var count = 0
        for (numbers in listOfNumbers) {
            var isRemoveAvailable = true
            var initMonotone: Int? = null

            var status = "SAFE"

            var currentIndex = 0
            var arbitraryIndex = 0

            if (!isSafe(numbers)) {
                println("Unsafe numbers $numbers")
            }
            while (currentIndex < numbers.size) {
                var change = "SAFE"
                if (++arbitraryIndex >= numbers.size) {
                    break
                }

                require(arbitraryIndex >= 0 && arbitraryIndex < numbers.size) { "arbitraryIndex $arbitraryIndex is out of bounds. Numbers $numbers" }
                require(currentIndex >= 0 && currentIndex < numbers.size) { "currentIndex $currentIndex is out of bounds. Numbers $numbers" }

                val monotone =
                    if (numbers[arbitraryIndex] - numbers[currentIndex] == 0) 0 else (numbers[arbitraryIndex] - numbers[currentIndex]) / abs(
                        numbers[arbitraryIndex] - numbers[currentIndex]
                    )

                if (initMonotone == null) {
                    initMonotone = monotone
                }

                if (monotone == 0 || initMonotone != monotone) {
                    change = "UNSAFE - monotone change"
                }

                if (monotone * (numbers[arbitraryIndex] - numbers[currentIndex]) !in (1..3).filter { it != 0 }) {
                    change = "UNSAFE - difference"
                }

                if (change.contains("UNSAFE") && isRemoveAvailable) {
                    isRemoveAvailable = false
                    if (currentIndex == 1) {
                        val right = numbers.subList(currentIndex, numbers.size)
                        if (isSafe(right)) {
                            numbers.removeAt(0)
                            break
                        }
                    }

                    if (arbitraryIndex == numbers.size - 1) {
                        val left = numbers.subList(0, arbitraryIndex)
                        if (isSafe(left)) {
                            numbers.removeAt(arbitraryIndex)
                            break
                        }
                    }

                    var left = numbers.subList(0, currentIndex)
                    var right = numbers.subList(arbitraryIndex, numbers.size)
                    if (isSafe(left + right)) {
                        println("Possible fix at $currentIndex element ${numbers[currentIndex]}")
                        numbers.removeAt(currentIndex)
                        if (currentIndex > 0) {
                            currentIndex--
                        } else {
                            initMonotone = null
                        }
                        arbitraryIndex = currentIndex
                        break
                    }
                    left = numbers.subList(0, currentIndex + 1)
                    right = numbers.subList(arbitraryIndex + 1, numbers.size)
                    if (isSafe(left + right)) {
                        println("Possible fix at $arbitraryIndex element ${numbers[arbitraryIndex]}")
                        numbers.removeAt(arbitraryIndex)
                        arbitraryIndex = currentIndex
                        break
                    }

                    println("Fix not possible at $currentIndex and $arbitraryIndex")
                    status = "UNSAFE"
                    break

                } else if (change.contains("UNSAFE") && !isRemoveAvailable) {
                    status = "UNSAFE"
                    break
                }
                currentIndex++
            }

            if (status == "SAFE") {
                require(isSafe(numbers)) { "Unsafe numbers $numbers" }
                count++
            }
        }

        return count
    }


    val testInput = readInput("day02/day02_test").filter { it.isNotBlank() }
    check(oneGoldStar(testInput) == 2)

    val input = readInput("day02/day02")
    oneGoldStar(input).println()

//    val randomElements = input.asSequence().shuffled().take(10).toList()
//    twoGoldStars(randomElements)
//    twoGoldStars(listOf("78, 76, 77, 79, 80, 81, 83, 86".replace(", ", " "))).println()

    check(twoGoldStars(testInput) == 4)
    twoGoldStars(input).println()
}