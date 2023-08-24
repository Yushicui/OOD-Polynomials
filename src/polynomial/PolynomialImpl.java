/**
 * CS5004 Project 4
 * Name: Yushi Cui
 */

package polynomial;
import java.util.LinkedList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Scanner;

/**
 * This is PolynomialImpl class which implements the Polynomial interface.
 */
public class PolynomialImpl implements Polynomial {

  /**
   * This inner class represents a term in the polynomial.
   */
  private class Term implements Comparable<Term> {
    int coefficient;
    int power;

    /**
     * Constructor for a term.
     *
     * @param coefficient the coefficient of the term
     * @param power the power of the term
     */
    private Term(int coefficient, int power) {
      this.coefficient = coefficient;
      this.power = power;
    }

    /**
     * This method is used to sort terms in descending order of power.
     *
     * @param other another term to compare with
     * @return a negative integer, zero, or a positive integer
     */
    @Override
    public int compareTo(Term other) {
      return Integer.compare(other.power, this.power);
    }
  }

  LinkedList<Term> terms;

  /**
   * Default constructor for a polynomial.
   * Initializes the terms as an empty LinkedList.
   */
  public PolynomialImpl() {
    this.terms = new LinkedList<>();
  }

  /**
   * Another constructor for a polynomial.
   * It takes a polynomial as a string, parses it and creates the polynomial accordingly.
   *
   * @param polynomial a string representation of a polynomial
   * @throws IllegalArgumentException if the input polynomial string is invalid
   */
  public PolynomialImpl(String polynomial) {
    this.terms = new LinkedList<>();
    Scanner scanner = new Scanner(polynomial);

    try {
      while (scanner.hasNext()) {
        String part = scanner.next();
        if (part.isEmpty()) continue;
        if (part.contains("x^")) {
          String[] termParts = part.split("x\\^");
          int coeff;
          if (termParts[0].isEmpty() || termParts[0].equals("+")) {
            coeff = 1;
          } else if (termParts[0].equals("-")) {
            coeff = -1;
          } else {
            coeff = Integer.parseInt(termParts[0]);
          }
          addTerm(coeff, Integer.parseInt(termParts[1]));
        } else if (part.contains("x")) {
          String coeffPart = part.replace("x", "");
          int coeff;
          if (coeffPart.isEmpty() || coeffPart.equals("+")) {
            coeff = 1;
          } else if (coeffPart.equals("-")) {
            coeff = -1;
          } else {
            coeff = Integer.parseInt(coeffPart);
          }
          addTerm(coeff, 1);
        } else {
          addTerm(Integer.parseInt(part), 0);
        }
      }
      scanner.close();
      Collections.sort(this.terms);
    }
    catch (Exception e) {
      throw new IllegalArgumentException("Invalid polynomial string");
    }
  }

  @Override
  public Polynomial add(Polynomial other) throws IllegalArgumentException {
    if (this.getClass() != other.getClass()) {
      throw new IllegalArgumentException("Must be same concrete type as the current object");
    }
    PolynomialImpl res = new PolynomialImpl();
    for (Term term : terms) {
      res.addTerm(term.coefficient, term.power);
    }
    for (Term term : (((PolynomialImpl) other).terms)) {
      res.addTerm(term.coefficient, term.power);
    }
    return res;
  }

  @Override
  public void addTerm(int coefficient, int power) throws IllegalArgumentException {
    if (power < 0) {
      throw new IllegalArgumentException("Power cannot be negative number");
    }
    if (coefficient == 0) {
      return;
    }
    for (Term term : terms) {
      if (term.power == power) {
        term.coefficient += coefficient;
        return;
      }
    }
    this.terms.add(new Term(coefficient, power));
    Collections.sort(this.terms);
  }

  @Override
  public boolean isSame(Polynomial poly) {

    if (this.getClass() != poly.getClass()) {
      return false;
    }

    PolynomialImpl other = (PolynomialImpl) poly;

    if (this.terms.isEmpty() && other.terms.isEmpty()) {
      return true;
    }

    Collections.sort(this.terms);
    Collections.sort(other.terms);

    if (terms.size() != other.terms.size()) {
      return false;
    }

    ListIterator<Term> thisTerm = terms.listIterator();
    ListIterator<Term> otherTerm = other.terms.listIterator();


    while (thisTerm.hasNext() && otherTerm.hasNext()) {
      Term t1 = thisTerm.next();
      Term t2 = otherTerm.next();

      if (t1.power != t2.power || t1.coefficient != t2.coefficient) {
        return false;
      }
    }
    return true;
  }

  @Override
  public double evaluate(double x) {
    double res = 0;
    for (Term term : terms) {
      res += term.coefficient * (Math.pow(x, term.power));
    }
    return res;
  }

  @Override
  public int getCoefficient(int power) {
    for (Term term : terms) {
      if (term.power == power) {
        return term.coefficient;
      }
    }
    return 0;
  }

  @Override
  public int getDegree() {
    if (this.terms.isEmpty()) {
      return 0;
    }
    return this.terms.get(0).power;
  }

  @Override
  public String toString() {
    if (terms.isEmpty()) {
      return "0";
    }
    StringBuilder string = new StringBuilder();
    for (Term term : terms) {
      if (term.coefficient == 0) {
        continue;
      }

      if (term.coefficient > 0 && string.length() > 0) {
        string.append(" +");
      }

      if (term.coefficient < 0) {
        string.append(" -");
      }

      if (Math.abs(term.coefficient) != 1 || term.power == 0) {
        string.append(Math.abs(term.coefficient));
      }

      if (term.power != 0) {
        string.append("x^").append(term.power);
      }
    }
    return string.toString().trim();
  }
  }




