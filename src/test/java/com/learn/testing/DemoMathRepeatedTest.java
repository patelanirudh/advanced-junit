package com.learn.testing;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DemoMathRepeatedTest {

    DemoMath demoMath;

    @BeforeEach
    void beforeEachTestMethod() {
        System.out.println("Executing @BeforeEach method for object instantiation");
        demoMath = new DemoMath();
    }

    @AfterEach
    void afterEachTestMethod() {
        System.out.println("Executing @AfterEach method");
    }

    @DisplayName("testSubtractValidParams")
    @RepeatedTest(value = 3, name = RepeatedTest.LONG_DISPLAY_NAME)
    void testSubtract_ValidParams_ShouldReturn10AndTrue(RepetitionInfo repetitionInfo, TestInfo testInfo) {
        System.out.println("Executing testcase: " + testInfo.getTestMethod().get().getName());

        // Arrange/Given
        // DemoMath demoMath = new DemoMath(); shifted to @BeforeEach
        int expectedResult = 10;

        // Act/When
        int result = demoMath.subtract(20, 10);

        // Assert/Then
        assertEquals(expectedResult, result, () -> "Result" + result + "is not the correct value");
        assertTrue(result > 0, () -> "Result" + result + " is not positive");
    }
}
