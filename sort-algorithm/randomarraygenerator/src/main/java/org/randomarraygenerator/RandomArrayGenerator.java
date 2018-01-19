package org.randomarraygenerator;

import java.util.Random;

/**
 *
 * @author krauses
 */
public class RandomArrayGenerator {

	private static Random random = new Random();

	/**
	 * Erzeugt ein Array mit zufälligen, nicht-negativen Werten vom Typ double.
	 *
	 * @param numValues
	 *            Länge des erzeugten Arrays
	 * @return ein Array vom Typ double mit den oben beschriebenen Eigenschaften
	 */
	public static double[] generateArray(final int numValues) {
		final double[] values = new double[numValues];
		if (RandomArrayGenerator.random.nextDouble() < 0.4) {
			final double q = 0.4;
			for (int i = 0; i < numValues; i++) {
				final double r = (100 * Math.log(RandomArrayGenerator.random.nextDouble() + 0.000000001)) / Math.log(q);
				values[i] = r;
			}
		} else {
			final int max_value = RandomArrayGenerator.random.nextInt(500) + 1000;
			for (int i = 0; i < numValues; i++) {
				final double r = max_value * RandomArrayGenerator.random.nextDouble();
				values[i] = r;
			}
		}
		return values;
	}

}