package it.unibo.io;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpleTest {

    @Test
    void additionTest() {
        int sum = 2 + 3;
        assertEquals(5, sum, "2 + 3 should equal 5");
    }
}