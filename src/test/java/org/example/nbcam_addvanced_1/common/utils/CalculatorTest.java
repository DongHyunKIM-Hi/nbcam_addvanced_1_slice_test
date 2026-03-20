package org.example.nbcam_addvanced_1.common.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    Calculator calculator;


    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        System.out.println("BeforeEach 실행");
    }

    @AfterEach
    void tearDown() {
        System.out.println("종료");
    }

    @Test
    @DisplayName("더하기 기능 테스트")
    void add() {
        int result = calculator.add(2,3);
        assertEquals(5, result);
    }

    @Test
    @DisplayName("빼기 기능 테스트")
    void subtract() {

        int a = 5;
        int b = 2;

        int result = calculator.subtract(a,b);

        assertEquals(3, result);

    }

    @Test
    @DisplayName(" 0을 나눌 때 발생하는 예외 테스트 ")
    void divideByZero() {

        assertThrows(ArithmeticException.class,
            () -> calculator.divide(10, 0));
    }

}