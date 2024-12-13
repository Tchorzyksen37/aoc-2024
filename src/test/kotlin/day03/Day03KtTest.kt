package day03

import Day03
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day03KtTest {

    companion object {
        @JvmStatic
        fun provideTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("mul(44,46)", 2024L),
                Arguments.of("mul(123,4)", 492L),
                Arguments.of("mul(4*", 0L),
                Arguments.of("mul(6,9!", 0L),
                Arguments.of("?(12,34)", 0L),
                Arguments.of("mul ( 2 , 4 )", 0L),
                Arguments.of("xmul(2,3)", 6L),
                Arguments.of("mul(2,3)x", 6L),
                Arguments.of("xmul(2,3)x", 6L)
            )
        }
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    fun `should extract mul command and calculate product`(input: String, expected: Long) {
        val result = Day03.calculate(input)
        assertEquals(expected, result)
    }
}