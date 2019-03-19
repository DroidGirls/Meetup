package net.yanzm.daggersample

import org.junit.Assert.assertEquals
import org.junit.Test

class SharpSurroundDecoratorTest {

    @Test
    fun test() {
        val decorator = SharpSurroundDecorator()
        assertEquals(
            """
                #########
                # hello #
                #########
            """.trimIndent(),
            decorator.decorate("hello")
        )
    }
}
