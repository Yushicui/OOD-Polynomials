/**
 * CS5004 Project 4
 * Name: Yushi Cui
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import polynomial.*;

public class PolynomialImplTest {
  /**
   * Test that verifies the no-argument constructor works correctly
   */
  @Test
  public void testNoArgConstructor() {
    PolynomialImpl p = new PolynomialImpl();
    assertEquals("0", p.toString());
    assertEquals(0, p.getDegree());
  }

  /**
   * Test that verifies the string argument constructor works correctly
   */
  @Test
  public void testStringArgConstructor() {
    /**
     * Test valid non-zero polynomial with more than one term with unique powers
     */
    PolynomialImpl p = new PolynomialImpl("4x^3 +3x^1 -5");
    assertEquals("4x^3 +3x^1 -5", p.toString());
    assertEquals(3, p.getDegree());

    PolynomialImpl p1 = new PolynomialImpl("-3x^4 -2x^5 -5 +11x^1");
    assertEquals("-2x^5 -3x^4 +11x^1 -5", p1.toString());
    assertEquals(5, p1.getDegree());

    /**
     * Test valid non-zero polynomial with more than one term with same powers
     */
    PolynomialImpl p2 = new PolynomialImpl("3x^2 +4x^2 -2x^2");
    assertEquals("5x^2", p2.toString());
    assertEquals(2, p2.getDegree());

    /**
     * Test string argument constructor works correctly when given a constant polynomial
     */
    PolynomialImpl p3 = new PolynomialImpl("2");
    assertEquals("2", p3.toString());
    assertEquals(0, p3.getDegree());

    /**
     * Test string argument constructor works correctly when given an empty string
     */
    PolynomialImpl p4 = new PolynomialImpl("");
    assertEquals("0", p4.toString());
    assertEquals(0, p4.getDegree());

    /**
     * Test string argument ThrowsArgumentException when given an invalid string
     */
    assertThrows(IllegalArgumentException.class, () -> { new PolynomialImpl("Invalid String");
    });

    assertThrows(IllegalArgumentException.class, () -> { new PolynomialImpl("3x^2+4x^1 -2");
    });
  }

  /**
   * Test toString works correctly when the polynomial has multiple terms
   */
  @Test
  public void testToString() {
    PolynomialImpl p = new PolynomialImpl("5x^2 +4x -2");
    assertEquals("5x^2 +4x^1 -2", p.toString());

    PolynomialImpl p1 = new PolynomialImpl("-50x^3 +x^2 +3");
    assertEquals("-50x^3 +x^2 +3", p1.toString());

    PolynomialImpl p2 = new PolynomialImpl("4x +2x^5 -3x^2 -10");
    assertEquals("2x^5 -3x^2 +4x^1 -10", p2.toString());
  }


  /**
   * Test add method works correctly
   */
  @Test
  public void testAdd() {
    /**
     * Test two polynomials with multiple terms and unique powers are added
     */
    PolynomialImpl p1 = new PolynomialImpl("2x^3 -3x^2 +4x^1 -10");
    PolynomialImpl p2 = new PolynomialImpl("-x^5 +2x^4");
    Polynomial result = p1.add(p2);
    assertEquals("-x^5 +2x^4 +2x^3 -3x^2 +4x^1 -10", result.toString());

    /**
     * Test two polynomials with multiple terms and same powers are added
     */
    PolynomialImpl p3 = new PolynomialImpl("x^3 -2x^2 +x^1 +5");
    Polynomial result2 = p1.add(p3);
    assertEquals("3x^3 -5x^2 +5x^1 -5", result2.toString());

    PolynomialImpl p4 = new PolynomialImpl("x^3 -2x^3 +x^3 +5x^3");
    Polynomial result3 = p3.add(p4);
    assertEquals("6x^3 -2x^2 +x^1 +5", result3.toString());
  }

  /**
   * Test addTerm method works correctly
   */
  @Test
  public void testAddTerm() {
    PolynomialImpl p = new PolynomialImpl();

    p.addTerm(5, 2);
    p.addTerm(3, 1);
    p.addTerm(-2, 0);
    assertEquals("5x^2 +3x^1 -2", p.toString());
    assertEquals(2, p.getDegree());

    p.addTerm(-2, 3);
    assertEquals("-2x^3 +5x^2 +3x^1 -2", p.toString());
    assertEquals(3, p.getDegree());

    p.addTerm(-3, 5);
    assertEquals("-3x^5 -2x^3 +5x^2 +3x^1 -2", p.toString());
    assertEquals(5, p.getDegree());

    p.addTerm(0, 4);
    assertEquals("-3x^5 -2x^3 +5x^2 +3x^1 -2", p.toString());
    assertEquals(5, p.getDegree());

    p.addTerm(1, 0);
    assertEquals("-3x^5 -2x^3 +5x^2 +3x^1 -1", p.toString());
    assertEquals(5, p.getDegree());

    /**
     * Test addTerm method throws an IllegalArgumentException if the power is negative
     */
    assertThrows(IllegalArgumentException.class, () -> { p.addTerm(1, -1);});
  }

  /**
   * Test getDegree method works correctly
   */
  @Test
  public void testGetDegree() {
    /**
     * Test the degree of a polynomial is a positive number
     */
    PolynomialImpl p1 = new PolynomialImpl("2x^2 -3x +1");
    assertEquals(2, p1.getDegree());

    /**
     * Test the polynomial is a constant
     */
    PolynomialImpl p2 = new PolynomialImpl("3");
    assertEquals(0, p2.getDegree());
  }

  /**
   * Test getCoefficient method works correctly
   */
  @Test
  public void testGetCoefficient() {
    /**
     * Test when a term with the given power exists in the polynomial
     */
    PolynomialImpl p = new PolynomialImpl("2x^3 -3x^2 +4x^1 -10");
    assertEquals(2, p.getCoefficient(3));
    assertEquals(-3, p.getCoefficient(2));
    assertEquals(4, p.getCoefficient(1));
    assertEquals(-10, p.getCoefficient(0));

    /**
     * Test when no term with the given power exists in the polynomial
     */
    PolynomialImpl p1 = new PolynomialImpl("2x^4 -3x^2");
    assertEquals(0, p1.getCoefficient(5));
    assertEquals(0, p1.getCoefficient(0));
  }

  /**
   * Test evaluate method works correctly
   */
  @Test
  public void testEvaluate() {
    /**
     * Test when evaluate for positive values of x
     */
    PolynomialImpl p1 = new PolynomialImpl("2x^3 -3x^2 +4x^1 -10");
    double result = p1.evaluate(2);
    assertEquals(2.0, result, 0.01);

    /**
     * Test when evaluate for negative values of x
     */
    PolynomialImpl p2 = new PolynomialImpl("2x^3 -3x^2 +4x^1 -10");
    double result2 = p2.evaluate(-2);
    assertEquals(-46, result2, 0.01);

    /**
     * Test when evaluate for x = 0
     */
    PolynomialImpl p3 = new PolynomialImpl("2x^3 -3x^2 +4x^1 -10");
    double result3 = p3.evaluate(0);
    assertEquals(-10, result3, 0.01);

  }

  /**
   * Test isSame method works correctly
   */
  @Test
  public void testIsSame() {
    /**
     * Test when two Polynomial are the same and in the same order
     */
    PolynomialImpl p1 = new PolynomialImpl("2x^3 -3x^2 +4x^1 -10");
    PolynomialImpl p2 = new PolynomialImpl("2x^3 -3x^2 +4x^1 -10");
    assertTrue(p1.isSame(p2));

    /**
     * Test when two Polynomial are the same but in the different order
     */
    PolynomialImpl p3 = new PolynomialImpl("-x^3 +x^1 +2x^2 +5");
    PolynomialImpl p4 = new PolynomialImpl("x^1 -x^3 +5 +2x^2");
    assertTrue(p3.isSame(p4));

    /**
     * Test when two Polynomial are empty
     */
    PolynomialImpl p5 = new PolynomialImpl("");
    PolynomialImpl p6 = new PolynomialImpl("");
    assertTrue(p5.isSame(p6));

    /**
     * Test when two Polynomial are different size but in same power
     */
    PolynomialImpl p7 = new PolynomialImpl("2x^3 -3x^2");
    PolynomialImpl p8 = new PolynomialImpl("x^3 -3x^2 +x^3");
    assertTrue(p7.isSame(p8));

    /**
     * Test when two Polynomial are different
     */
    assertFalse(p1.isSame(p3));
  }
}

