import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day14KtTest {

    companion object {
        @JvmStatic
        fun provideTestInput(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf("p=0,4 v=3,-3"), listOf(createRobot(Pair(0, 4), Pair(3, -3)))
                ),
                Arguments.of(
                    listOf("p=0,4 v=3,3", "p=-1,-5 v=-3,-3"),
                    listOf(createRobot(Pair(0, 4), Pair(3, 3)), createRobot(Pair(-1, -5), Pair(-3, -3)))
                )
            )
        }

        @JvmStatic
        fun provideTestForRobotMove(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    createRobot(Pair(2, 4), Pair(2, -3)),
                    Pair(4, 1)
                ),
                Arguments.of(
                    createRobot(Pair(4, 1), Pair(2, -3)),
                    Pair(6, 5)
                ),
                Arguments.of(
                    createRobot(Pair(6, 5), Pair(2, -3)),
                    Pair(8, 2)
                ),
                Arguments.of(
                    createRobot(Pair(8, 2), Pair(2, -3)),
                    Pair(10, 6)
                ),
                Arguments.of(
                    createRobot(Pair(10, 6), Pair(2, -3)),
                    Pair(1, 3)
                ),
                Arguments.of(
                    createRobot(Pair(0, 0), Pair(-2, 3)),
                    Pair(9, 3)
                ),
                Arguments.of(
                    createRobot(Pair(5, 6), Pair(5, 4)),
                    Pair(10, 3)
                )
            )
        }

        private fun createRobot(pos: Pair<Int, Int>, vel: Pair<Int, Int>): Robot {
            return Robot(pos, vel, Pair(10, 6))
        }
    }

    @ParameterizedTest
    @MethodSource("provideTestInput")
    fun shouldParseInputToRobotList(input: List<String>, expected: List<Robot>) {
        // expect
        val actual = Day14(10, 6, 5).parse(input)
        assert(actual == expected)
    }

    @ParameterizedTest
    @MethodSource("provideTestForRobotMove")
    fun shouldMoveRobot(robot: Robot, expected: Pair<Int, Int>) {
        // when
        robot.move()

        // then
        assert(robot.p == expected)
    }

}