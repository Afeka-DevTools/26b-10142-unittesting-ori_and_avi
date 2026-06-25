/*
 * Unit tests for org.example.App
 *
 * Course: Development Tools (Afeka) - HW2, Unit Testing with Gradle & JUnit 5
 * Team: ori_and_avi (Ori Ohayon, Avy Kalifa)
 *
 * The goal of this suite is FULL path / branch coverage of every method in App.java:
 * for each method we cover the "happy path", the boundary values, the edge cases and
 * (where relevant) the exceptional path. A wide range of JUnit 5 assertions is used on
 * purpose: assertEquals, assertNotEquals, assertTrue, assertFalse, assertNull,
 * assertNotNull, assertThrows, assertDoesNotThrow, assertArrayEquals, assertIterableEquals
 * and assertAll.
 */
package org.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("App unit tests")
class AppTest {

    private static final double DELTA = 1e-9;

    @Test
    @DisplayName("the utility class is instantiable (covers the default constructor)")
    void utilityClassIsInstantiable() {
        assertNotNull(new App());
    }

    // ---------------------------------------------------------------------
    @Nested
    @DisplayName("add(int, int)")
    class AddTests {

        @Test
        @DisplayName("adds positive numbers")
        void addsPositiveNumbers() {
            assertEquals(5, App.add(2, 3));
        }

        @Test
        @DisplayName("handles negative and zero operands")
        void handlesNegativeAndZero() {
            assertAll(
                    () -> assertEquals(-1, App.add(2, -3)),
                    () -> assertEquals(-5, App.add(-2, -3)),
                    () -> assertEquals(0, App.add(0, 0)),
                    () -> assertEquals(7, App.add(7, 0))
            );
        }

        @Test
        @DisplayName("is commutative")
        void isCommutative() {
            assertEquals(App.add(8, 4), App.add(4, 8));
        }

        @Test
        @DisplayName("documents int overflow (wrap-around) behaviour")
        void overflowWrapsAround() {
            // Integer.MAX_VALUE + 1 overflows to Integer.MIN_VALUE
            assertEquals(Integer.MIN_VALUE, App.add(Integer.MAX_VALUE, 1));
        }
    }

    // ---------------------------------------------------------------------
    @Nested
    @DisplayName("isPrime(int)")
    class IsPrimeTests {

        @ParameterizedTest(name = "{0} is prime")
        @ValueSource(ints = {2, 3, 5, 7, 11, 13, 97, 7919})
        void returnsTrueForPrimes(int n) {
            assertTrue(App.isPrime(n));
        }

        @ParameterizedTest(name = "{0} is NOT prime")
        @ValueSource(ints = {4, 6, 8, 9, 15, 25, 100})
        void returnsFalseForComposites(int n) {
            assertFalse(App.isPrime(n));
        }

        @Test
        @DisplayName("numbers below 2 (incl. negatives) are not prime")
        void belowTwoIsNotPrime() {
            assertAll(
                    () -> assertFalse(App.isPrime(1)),
                    () -> assertFalse(App.isPrime(0)),
                    () -> assertFalse(App.isPrime(-1)),
                    () -> assertFalse(App.isPrime(-7))
            );
        }

        @Test
        @DisplayName("2 and 3 are prime (loop body never executes)")
        void smallPrimesBoundary() {
            assertTrue(App.isPrime(2));
            assertTrue(App.isPrime(3));
        }
    }

    // ---------------------------------------------------------------------
    @Nested
    @DisplayName("reverse(String)")
    class ReverseTests {

        @Test
        @DisplayName("reverses a normal string")
        void reversesNormalString() {
            assertEquals("cba", App.reverse("abc"));
        }

        @Test
        @DisplayName("empty and single-char strings")
        void emptyAndSingleChar() {
            assertEquals("", App.reverse(""));
            assertEquals("a", App.reverse("a"));
        }

        @Test
        @DisplayName("reversing twice returns the original")
        void doubleReverseIsIdentity() {
            String original = "Hello, World!";
            assertEquals(original, App.reverse(App.reverse(original)));
        }

        @Test
        @DisplayName("null input throws NullPointerException")
        void nullThrows() {
            assertThrows(NullPointerException.class, () -> App.reverse(null));
        }
    }

    // ---------------------------------------------------------------------
    @Nested
    @DisplayName("factorial(int)")
    class FactorialTests {

        @ParameterizedTest(name = "factorial({0}) = {1}")
        @CsvSource({
                "0, 1",   // loop body never executes
                "1, 1",   // loop body never executes
                "2, 2",
                "3, 6",
                "5, 120",
                "10, 3628800"
        })
        void computesFactorial(int n, int expected) {
            assertEquals(expected, App.factorial(n));
        }

        @Test
        @DisplayName("negative input throws IllegalArgumentException")
        void negativeThrows() {
            IllegalArgumentException ex =
                    assertThrows(IllegalArgumentException.class, () -> App.factorial(-3));
            assertEquals("Negative number", ex.getMessage());
        }

        @Test
        @DisplayName("non-negative input never throws")
        void nonNegativeDoesNotThrow() {
            assertDoesNotThrow(() -> App.factorial(6));
        }
    }

    // ---------------------------------------------------------------------
    @Nested
    @DisplayName("isPalindrome(String)")
    class IsPalindromeTests {

        @Test
        @DisplayName("true for palindromes, ignoring case and punctuation")
        void truePalindromes() {
            assertAll(
                    () -> assertTrue(App.isPalindrome("racecar")),
                    () -> assertTrue(App.isPalindrome("RaceCar")),
                    () -> assertTrue(App.isPalindrome("A man, a plan, a canal: Panama")),
                    () -> assertTrue(App.isPalindrome("12321"))
            );
        }

        @Test
        @DisplayName("false for non-palindromes")
        void falseNonPalindromes() {
            assertFalse(App.isPalindrome("Hello"));
            assertFalse(App.isPalindrome("abc"));
        }

        @Test
        @DisplayName("empty string and punctuation-only collapse to a palindrome")
        void emptyAndPunctuationOnly() {
            assertTrue(App.isPalindrome(""));
            assertTrue(App.isPalindrome("!!!"));   // cleaned to "" -> palindrome
            assertTrue(App.isPalindrome("a"));
        }
    }

    // ---------------------------------------------------------------------
    @Nested
    @DisplayName("fibonacciUpTo(int)")
    class FibonacciTests {

        @Test
        @DisplayName("returns the Fibonacci sequence up to n (inclusive)")
        void returnsSequence() {
            List<Integer> expected = Arrays.asList(0, 1, 1, 2, 3, 5, 8);
            assertIterableEquals(expected, App.fibonacciUpTo(8));
        }

        @Test
        @DisplayName("stops correctly when n is not a Fibonacci number")
        void stopsBelowLimit() {
            // 13 > 10, so it is excluded
            assertEquals(Arrays.asList(0, 1, 1, 2, 3, 5, 8), App.fibonacciUpTo(10));
        }

        @Test
        @DisplayName("boundary values n=0 and n=1")
        void boundaryValues() {
            assertEquals(Arrays.asList(0), App.fibonacciUpTo(0));
            assertEquals(Arrays.asList(0, 1, 1), App.fibonacciUpTo(1));
        }

        @Test
        @DisplayName("negative input throws IllegalArgumentException")
        void negativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> App.fibonacciUpTo(-1));
        }
    }

    // ---------------------------------------------------------------------
    @Nested
    @DisplayName("charFrequency(String)")
    class CharFrequencyTests {

        @Test
        @DisplayName("counts characters, case sensitive")
        void countsCharacters() {
            Map<Character, Integer> freq = App.charFrequency("aabBc");
            assertAll(
                    () -> assertEquals(2, freq.get('a')),
                    () -> assertEquals(1, freq.get('b')),
                    () -> assertEquals(1, freq.get('B')),
                    () -> assertEquals(1, freq.get('c')),
                    () -> assertEquals(4, freq.size())
            );
        }

        @Test
        @DisplayName("empty string yields an empty map")
        void emptyString() {
            Map<Character, Integer> freq = App.charFrequency("");
            assertNotNull(freq);
            assertTrue(freq.isEmpty());
        }

        @Test
        @DisplayName("counts repeated chars and spaces, absent char is null")
        void repeatedCharsAndSpaces() {
            Map<Character, Integer> freq = App.charFrequency("a a");
            assertEquals(2, freq.get('a'));
            assertEquals(1, freq.get(' '));
            assertNull(freq.get('z'));
        }
    }

    // ---------------------------------------------------------------------
    @Nested
    @DisplayName("isAnagram(String, String)")
    class IsAnagramTests {

        @Test
        @DisplayName("true for valid anagrams (case & whitespace insensitive)")
        void validAnagrams() {
            assertAll(
                    () -> assertTrue(App.isAnagram("listen", "silent")),
                    () -> assertTrue(App.isAnagram("Dormitory", "Dirty room")),
                    () -> assertTrue(App.isAnagram("Listen", "SILENT")),
                    () -> assertTrue(App.isAnagram("", ""))
            );
        }

        @Test
        @DisplayName("false when characters or length differ")
        void notAnagrams() {
            assertFalse(App.isAnagram("hello", "bello"));
            assertFalse(App.isAnagram("abc", "abcd"));
        }
    }

    // ---------------------------------------------------------------------
    @Nested
    @DisplayName("average(int[])")
    class AverageTests {

        @Test
        @DisplayName("computes the average of a non-empty array")
        void averageNonEmpty() {
            assertEquals(2.5, App.average(new int[]{2, 3}), DELTA);
            assertEquals(2.5, App.average(new int[]{1, 2, 3, 4}), DELTA);
            assertEquals(5.0, App.average(new int[]{5}), DELTA);
        }

        @Test
        @DisplayName("handles negative numbers")
        void averageWithNegatives() {
            assertEquals(0.0, App.average(new int[]{-2, 2}), DELTA);
        }

        @Test
        @DisplayName("empty array throws IllegalArgumentException")
        void emptyThrows() {
            IllegalArgumentException ex =
                    assertThrows(IllegalArgumentException.class, () -> App.average(new int[]{}));
            assertEquals("Empty array", ex.getMessage());
        }
    }

    // ---------------------------------------------------------------------
    @Nested
    @DisplayName("filterEvens(List<Integer>)")
    class FilterEvensTests {

        @Test
        @DisplayName("keeps only even numbers (including zero and negatives)")
        void filtersEvens() {
            assertEquals(Arrays.asList(2, 4, 6), App.filterEvens(Arrays.asList(1, 2, 3, 4, 5, 6)));
            assertEquals(Arrays.asList(0, -2, -4), App.filterEvens(Arrays.asList(0, -1, -2, -3, -4)));
        }

        @Test
        @DisplayName("returns empty list when there are no evens")
        void noEvens() {
            assertTrue(App.filterEvens(Arrays.asList(1, 3, 5)).isEmpty());
        }

        @Test
        @DisplayName("empty input returns empty list")
        void emptyInput() {
            assertEquals(Collections.emptyList(), App.filterEvens(Collections.emptyList()));
        }

        @Test
        @DisplayName("all-even input is returned unchanged")
        void allEvens() {
            List<Integer> input = Arrays.asList(2, 4, 8);
            assertIterableEquals(input, App.filterEvens(input));
        }
    }

    // ---------------------------------------------------------------------
    @Nested
    @DisplayName("mostCommonWord(String)")
    class MostCommonWordTests {

        @Test
        @DisplayName("finds the most frequent word, case-insensitively")
        void findsTopWord() {
            assertEquals("hello", App.mostCommonWord("Hello world hello HELLO hi"));
        }

        @Test
        @DisplayName("ignores punctuation when splitting words")
        void ignoresPunctuation() {
            assertEquals("cat", App.mostCommonWord("cat, cat. dog!"));
        }

        @Test
        @DisplayName("single word text returns that word")
        void singleWord() {
            assertEquals("word", App.mostCommonWord("word"));
            assertNotEquals("Word", App.mostCommonWord("Word")); // result is lower-cased
        }
    }
}
