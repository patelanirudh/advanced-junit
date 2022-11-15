package com.learn.testing;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test math operations in DemoMath class")
class DemoMathTest {
    // test<System Under Test>_<Condition or State Change>_<Expected Result>
    DemoMath demoMath;

    @BeforeAll
    static void setup() {
        System.out.println("Executing @BeforeAll method");
    }

    @AfterAll
    static void cleanup() {
        System.out.println("Executing @AfterAll method");
    }

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
    @Test
    void testSubtract_ValidParams_ShouldReturn10AndTrue() {
        System.out.println("Executing @Test testSubtractValidParams");
        // Arrange/Given
        // DemoMath demoMath = new DemoMath(); shifted to @BeforeEach
        int expectedResult = 10;

        // Act/When
        int result = demoMath.subtract(20, 10);

        // Assert/Then
        assertEquals(expectedResult, result, () -> "Result" + result + "is not the correct value");
        assertTrue(result > 0, () -> "Result" + result + " is not positive");
    }

    @DisplayName("testSubtractInvalidParams")
    @Test
    void testSubtract_InvalidParams_ShouldReturnIncorrectValueAndFalse() {
        System.out.println("Executing @Test testSubtractInvalidParams");
        // Arrange/Given
        int expectedResult = 20;
        // DemoMath demoMath = new DemoMath(); shifted to @BeforeEach

        // Act/When
        int result = demoMath.subtract(10, 30); // will return -20

        // Assert/Then
        assertNotEquals(expectedResult, result, () -> "Result " + result + " is not the correct value");
        // Below boolean assertion will only run if above assertion is passed
        assertFalse(result > 0, () -> "Result " + result + " is positive");
    }


    @Disabled("TODO: Not implemented yet. Need to work on it...")
    @DisplayName("testSubtract_ForcedFailure")
    @Test
    void testSubtract_ForceFailure() {
        fail("Not implemented yet. Force failure !!!");
    }

    @DisplayName("testDivisionByZero")
    @Test
    void testDivision_DivisionByZero_ShouldThrowException() {
        System.out.println("Executing @Test testDivisionByZero");

        // Arrange
        int dividend = 50;
        int divisor = 0;
        String expectedExceptionMessage = "/ by zero";

        // Act & Assert
        ArithmeticException actualException = assertThrows(ArithmeticException.class, () -> {
            // Act
            demoMath.division(dividend, divisor);
        }, "Division by zero should have thrown an Arithmetic Exception");

        // Assert
        assertEquals(expectedExceptionMessage, actualException.getMessage(), "Unexpected exception message");
    }

    @DisplayName("testMultiply_ValidInputParams[]")
    @ParameterizedTest
    @MethodSource("multiplicationStreamParams")
        // name can be omitted if it same as testMethodName(as below)
    void testMultiply_ValidInputParams_ShouldReturnExpectedResults(int number1, int number2, int expectedResult) {
        System.out.println("Executing @Test testMultiply_ValidInputParams[] : " + number1 + "*" + number2 + "=" + expectedResult);

        // Act
        int actualResult = demoMath.multiply(number1, number2);

        // Assert
        assertEquals(expectedResult, actualResult, () -> "Multiplication actualResult" + actualResult + "is incorrect");
    }

    private static Stream<Arguments> multiplicationStreamParams() {
        return Stream.of(
                Arguments.of(2, 3, 6),
                Arguments.of(5, 5, 25),
                Arguments.of(10, 3, 30)
        );
    }

    // TestMethod to showcase a different way to have parameters passed in test method.
    @DisplayName("testMultiply_ValidInputCSVParams[]")
    @ParameterizedTest
    @CsvSource({       // For String
            "3,3,9",    //"apple,orange",
            "5,6,30",   //"apple,''", empty second string
            "11,3,33"   //"john, wayne"
    })
    void testMultiply_ValidInputCSVParams_ShouldReturnExpectedResults(int number1, int number2, int expectedResult) {
        System.out.println("Executing @Test testMultiply_ValidInputCSVParams[] : " + number1 + "*" + number2 + "=" + expectedResult);

        // Act
        int actualResult = demoMath.multiply(number1, number2);

        // Assert
        assertEquals(expectedResult, actualResult, () -> "Multiplication actualResult" + actualResult + "is incorrect");
    }

    @DisplayName("testMultiply_ValidInputCSVFileParams")
    @ParameterizedTest
    @CsvFileSource(resources = "/mathMultiplication.csv")
    void testMultiply_ValidInputCSVFileParams_ShouldReturnExpectedResults(int number1, int number2, int expectedResult) {
        System.out.println("Executing @Test testMultiply_ValidInputCSVFileParams : " + number1 + "*" + number2 + "=" + expectedResult);

        // Act
        int actualResult = demoMath.multiply(number1, number2);

        // Assert
        assertEquals(expectedResult, actualResult, () -> "Multiplication actualResult" + actualResult + "is incorrect");
    }

    @DisplayName("testValueSource_WithString")
    @ParameterizedTest
    @ValueSource(strings = {"John", "kate", "Alice"})
        // can work for other data types as well
    void testValueSource_WithString(String firstName) {
        System.out.println("Executing @Test testValueSource_WithString : " + firstName);
        assertNotNull(firstName);
    }

}