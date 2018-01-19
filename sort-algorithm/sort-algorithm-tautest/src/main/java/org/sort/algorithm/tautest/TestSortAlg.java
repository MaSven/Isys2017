package org.sort.algorithm.tautest;

import java.util.Arrays;

import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.randomarraygenerator.RandomArrayGenerator;

import space.smarquardt.sortalgorithm.implementations.RangedSort;
import space.smarquardt.sortalgorithm.implementations.impl.Mergesort;
import space.smarquardt.sortalgorithm.implementations.impl.OwnSort;
import space.smarquardt.sortalgorithm.implementations.impl.Quicksort;

public class TestSortAlg {

	public static void main(final String[] args) {
		final int count = 300;
		final int[] lengthOfArray = { 100, 120, 140, 160 };
		RangedSort sort;
		for (final int length : lengthOfArray) {
			final double[] testArray = RandomArrayGenerator.generateArray(length);
			sort = new Quicksort(count, testArray);
			TestSortAlg.sortAndPrintResult(length, testArray, sort);
			sort = new Mergesort(count, testArray);
			TestSortAlg.sortAndPrintResult(length, testArray, sort);
			sort = new OwnSort(count, testArray);
			TestSortAlg.sortAndPrintResult(length, testArray, sort);

		}
	}

	/**
	 * @param length
	 * @param testArray
	 * @param sort
	 */
	private static void sortAndPrintResult(final int length, final double[] testArray, final RangedSort sort) {
		sort.sort();
		final double[] resultArray = sort.getSortedArrayTillRange();
		System.out.println("Tau mit " + length + " länge und " + sort.nameOfAlgorithm() + " ergibt: "
				+ new KendallsCorrelation().correlation(resultArray, testArray) + " länge des test array: "
				+ resultArray.length + " und inhalt " + Arrays.toString(resultArray));
	}

}
