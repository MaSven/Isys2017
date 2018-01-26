package space.smarquardt.sortalgorithm.implementations.impl;

/**
 * Vergleicht zwei double werte
 *
 * @author Sven Marquardt
 *
 */
public interface DoubleComparator {
	/**
	 * Liefert true wenn die erste nummer größer ist als die zweite
	 *
	 * @param firstNumber
	 *            Erste nummer
	 * @param secondNumber
	 *            Zweite nummer
	 * @return true wenn die erste nummer kleiner ist als die zweite ansonsten false
	 */
	boolean isLessThan(double firstNumber, double secondNumber);
}
