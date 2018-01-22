package org.sort.algorithm.tautest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.randomarraygenerator.RandomArrayGenerator;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.io.Files;

import space.smarquardt.sortalgorithm.implementations.RangedSort;
import space.smarquardt.sortalgorithm.implementations.impl.Mergesort;
import space.smarquardt.sortalgorithm.implementations.impl.OwnSort;
import space.smarquardt.sortalgorithm.implementations.impl.Quicksort;

public class TestSortAlg {

	public static void main(final String[] args) {
		final int count = 300;
		final int[] lengthOfArray = { 100, 120, 140, 160, 200 };
		final ListMultimap<Integer, Double> resultsQuickSort = MultimapBuilder.hashKeys().arrayListValues().build();
		final ListMultimap<Integer, Double> resultsMergeSort = MultimapBuilder.hashKeys().arrayListValues().build();
		final ListMultimap<Integer, Double> resultsOwnSort = MultimapBuilder.hashKeys().arrayListValues().build();
		RangedSort sort;
		for (int i = 1000; i > 0; i--) {
			for (final int length : lengthOfArray) {
				final double[] testArray = RandomArrayGenerator.generateArray(length);
				double result;
				sort = new Quicksort(count, testArray);
				result = TestSortAlg.sortAndPrintResult(length, testArray, sort);
				resultsQuickSort.put(length, result);
				sort = new Mergesort(count, testArray);
				result = TestSortAlg.sortAndPrintResult(length, testArray, sort);
				resultsMergeSort.put(length, result);
				sort = new OwnSort(count, testArray);
				result = TestSortAlg.sortAndPrintResult(length, testArray, sort);
				resultsOwnSort.put(length, result);
			}
		}

		// final Percentile percentile = new Percentile();
		// for (final int length : lengthOfArray) {
		// double[] results = ArrayUtils
		// .toPrimitive(resultsQuickSort.get(length).toArray(new
		// Double[resultsQuickSort.get(length).size()]));
		// System.out.println("Ergebnis Quicksort: " + percentile.evaluate(results,
		// 10.0));
		// results = ArrayUtils
		// .toPrimitive(resultsMergeSort.get(length).toArray(new
		// Double[resultsMergeSort.get(length).size()]));
		// System.out.println("Ergebnis MergeSort: " + percentile.evaluate(results,
		// 10.0));
		// results = ArrayUtils
		// .toPrimitive(resultsOwnSort.get(length).toArray(new
		// Double[resultsOwnSort.get(length).size()]));
		// System.out.println("Ergebnis OwnSort: " + percentile.evaluate(results,
		// 10.0));
		// }
		final StringBuilder builder = new StringBuilder();
		for (final Integer key : resultsOwnSort.keySet()) {
			builder.append(key).append("\n");
			final List<Double> results = resultsOwnSort.get(key);
			results.sort(Double::compareTo);
			results.forEach(v -> builder.append(v).append("\n"));
			try {
				Files.write(builder.toString().getBytes(), new File("OwnSort" + key + "csv"));
			} catch (final IOException e) {
				e.printStackTrace();
			}
			builder.delete(0, builder.length());
		}

	}

	/**
	 *
	 * @param length
	 * @param testArray
	 * @param sort
	 */
	private static double sortAndPrintResult(final int length, final double[] testArray, final RangedSort sort) {
		sort.sort();
		final double[] resultArray = sort.getSortedArrayTillRange();
		final double result = new KendallsCorrelation().correlation(resultArray, testArray);
		// System.out.println("Tau mit " + length + " länge und " +
		// sort.nameOfAlgorithm() + " ergibt: " + result
		// + " länge des test array: " + resultArray.length + " und inhalt " +
		// Arrays.toString(resultArray));
		return result;
	}

}
