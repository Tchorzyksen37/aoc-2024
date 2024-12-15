class Day14(private val boardX: Int, private val boardY: Int, private val secondsElapsed: Int) {

    fun part1(input: List<String>): Int {
        val robots = parse(input)
        robots.forEach { it.moveTimes(secondsElapsed) }

        val firstQuadrant = robots.filter { it.p.first < boardX / 2 && it.p.second < boardY / 2 }
        val secondQuadrant = robots.filter { it.p.first > boardX / 2 && it.p.second < boardY / 2 }
        val thirdQuadrant = robots.filter { it.p.first < boardX / 2 && it.p.second > boardY / 2 }
        val fourthQuadrant = robots.filter { it.p.first > boardX / 2 && it.p.second > boardY / 2 }

        return firstQuadrant.count() * secondQuadrant.count() * thirdQuadrant.count() * fourthQuadrant.count()
    }

    fun part2(input: List<String>): Int {
        val robots = parse(input)
        var count = 0
        while (true) {
            count++
            robots.forEach { it.moveTimes(1) }
            if (robots.map { it.p }.distinct().count() == input.size) {
                printBoard(robots)
                println("Seconds elapsed: $count")
                break
            }
        }

        return count
    }


    fun parse(input: List<String>): List<Robot> {
        val list = mutableListOf<Robot>()
        for (line in input) {
            val robot =
                Regex("""p=(?<px>-?\d+),(?<py>-?\d+) v=(?<vx>-?\d+),(?<vy>-?\d+)""").findAll(line).map {
                    val px = it.groups["px"]?.value!!.toInt()
                    val py = it.groups["py"]?.value!!.toInt()
                    val vx = it.groups["vx"]?.value!!.toInt()
                    val vy = it.groups["vy"]?.value!!.toInt()
                    Robot(Pair(px, py), Pair(vx, vy), Pair(boardX, boardY))
                }.first()
            list.add(robot)
        }

        return list
    }

    fun printBoard(robots: List<Robot>) {
        val board = Array(boardY + 1) { Array(boardX + 1) { '.' } }
        robots.forEach {
            board[it.p.second][it.p.first] = '*'
        }

        for (row in board) {
            println(row.joinToString(""))
        }
    }

}

data class Robot(var p: Pair<Int, Int>, val v: Pair<Int, Int>, private val edges: Pair<Int, Int>) {
    fun move() {
        fun calculateNextPosition(p: Pair<Int, Int>, v: Pair<Int, Int>): Pair<Int, Int> {
            var x = p.first + v.first
            var y = p.second + v.second

            if (x < 0) {
                x += edges.first
                x++
            }

            if (x > edges.first) {
                x -= edges.first
                x--
            }

            if (y < 0) {
                y += edges.second
                y++
            }

            if (y > edges.second) {
                y -= edges.second
                y--
            }

            return Pair(x, y)
        }

        p = calculateNextPosition(p, v)
    }

    fun moveTimes(times: Int) {
        repeat(times) {
            move()
        }
    }
}

fun main() {
    val testInput = listOf(
        "p=0,4 v=3,-3",
        "p=6,3 v=-1,-3",
        "p=10,3 v=-1,2",
        "p=2,0 v=2,-1",
        "p=0,0 v=1,3",
        "p=3,0 v=-2,-2",
        "p=7,6 v=-1,-3",
        "p=3,0 v=-1,-2",
        "p=9,3 v=2,3",
        "p=7,3 v=-1,2",
        "p=2,4 v=2,-3",
        "p=9,5 v=-3,-3"
    )

    val part1Test = Day14(10, 6, 100)
    val part1TestAns = part1Test.part1(testInput)
    check(part1TestAns == 12) { "Test failed, got $part1TestAns" }

    val input = readInput("day14")
    val day14 = Day14(100, 102, 100)
    day14.part1(input).println()

    day14.part2(input)
}