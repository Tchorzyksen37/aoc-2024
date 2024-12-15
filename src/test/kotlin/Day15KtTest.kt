import org.junit.jupiter.api.Test

class Day15KtTest {

    @Test
    fun testInitialization() {
        // given:
        val input = listOf(
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

        // when:
        val day15 = Day15(input)

        // then:
        assert(day15.map.isNotEmpty()) { "Map should not be empty." }
        assert(day15.directions == "<^^>>>vv<v>>v<<") { "Directions not parsed correctly." }
        assert(day15.currentPos == Pair(2, 2)) { "Starting position should be (2, 2)." }
    }

}