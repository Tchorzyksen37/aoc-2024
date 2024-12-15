const val DEBUG = false

fun debug(block: () -> Unit) {
    if (DEBUG) {
        block()
    }
}

class Day15(input: List<String>) {
    var map: MutableMap<Pair<Int, Int>, Char> = mutableMapOf()
    var directions: String = ""
    var currentPos: Pair<Int, Int> = Pair(0, 0)

    init {
        parse(input)
    }

    fun part1(): Int {
        debug {
            println("Initial state")
            printMap()
            println("-".repeat(20) + "\n")
        }

        for (direction in directions) {
            debug { println("Executing $direction") }
            execute(direction)

            debug {
                printMap()
                println("-".repeat(20) + "\n")
            }
        }

        return map.filter { it.value == 'O' }.keys.sumOf { 100 * it.second + it.first }
    }

    private fun execute(moveDirection: Char) {
        when (moveDirection) {
            '^' -> move(0, -1)
            'v' -> move(0, 1)
            '<' -> move(-1, 0)
            '>' -> move(1, 0)
        }
    }

    private fun move(x: Int, y: Int) {
        val newPos = Pair(currentPos.first + x, currentPos.second + y)
        if (map[newPos] == '#') {
            debug { println("Hit a wall at $newPos") }
        }

        if (map[newPos] == '.') {
            debug { println("Moved to $newPos") }
            map[currentPos] = '.'
            map[newPos] = '@'
            currentPos = newPos
        }

        if (map[newPos] == 'O') {
            val status = movePackage(newPos.first, newPos.second, Pair(x, y))
            if (status) {
                map[currentPos] = '.'
                map[newPos] = '@'
                currentPos = newPos
            }
        }
    }

    private fun movePackage(x: Int, y: Int, move: Pair<Int, Int>): Boolean {
        val newPos = Pair(x + move.first, y + move.second)

        if (map[newPos] == '#') {
            debug { println("Package is blocked at $newPos") }
            return false
        }

        if (map[newPos] == '.') {
            debug { println("Moved package to $x, $y") }
            map[newPos] = 'O'
            return true
        }

        if (map[newPos] == 'O') {
            val status = movePackage(newPos.first, newPos.second, move)
            if (status) {
                debug { println("Moved package to $x, $y") }
                map[newPos] = 'O'
            }

            return status
        }

        throw IllegalStateException("Unknown map state at ${newPos.first}, ${newPos.second}")
    }

    private fun parse(input: List<String>) {
        val mapList = input.takeWhile { it.isNotEmpty() }
        val directionsList = input.dropWhile { it.isNotEmpty() }.drop(1)
        map = parseMap(mapList)
        directions = parseDirections(directionsList)
    }

    private fun parseMap(mapList: List<String>): MutableMap<Pair<Int, Int>, Char> {
        return mapList.mapIndexed { y, row ->
            row.mapIndexed { x, char ->
                if (char == '@') {
                    currentPos = Pair(x, y)
                }
                Pair(x, y) to char
            }
        }.flatten().toMap().toMutableMap()
    }

    private fun parseDirections(directionsList: List<String>): String {
        return directionsList.joinToString("")
    }

    private fun printMap() {
        val maxX = map.keys.maxByOrNull { it.first }!!.first
        val maxY = map.keys.maxByOrNull { it.second }!!.second

        for (y in 0..maxY) {
            for (x in 0..maxX) {
                print(map[Pair(x, y)])
            }
            println("")
        }
    }
}

fun main() {
    if (DEBUG) {
        val debugInput = listOf(
            "########",
            "#..O.O.#",
            "##@.O..#",
            "#...O..#",
            "#.#.O..#",
            "#...O..#",
            "#......#",
            "########",
            "",
            "<^^>>>vv<v>>v<<"
        )

        val debugDay15Part1 = Day15(debugInput)
        debugDay15Part1.part1().println()
    }

    val testInput = listOf(
        "##########",
        "#..O..O.O#",
        "#......O.#",
        "#.OO..O.O#",
        "#..O@..O.#",
        "#O#..O...#",
        "#O..O..O.#",
        "#.OO.O.OO#",
        "#....O...#",
        "##########",
        "",
        "<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^",
        "vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v",
        "><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<",
        "<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^",
        "^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><",
        "^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^",
        ">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^",
        "<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>",
        "^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>",
        "v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^"
    )

    val testDay15 = Day15(testInput)
    val part1TestAns = testDay15.part1()
    check(part1TestAns == 10092) { "Test failed, got $part1TestAns" }

    val input = readInput("day15")
    val day15 = Day15(input)
    day15.part1().println()
}