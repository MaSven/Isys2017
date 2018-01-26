package org.sort.algorithm.tautest;

import java.io.File;
import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.randomarraygenerator.RandomArrayGenerator;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.io.Files;

import space.smarquardt.sortalgorithm.implementations.RangedSort;
import space.smarquardt.sortalgorithm.implementations.impl.Mergesort;
import space.smarquardt.sortalgorithm.implementations.impl.OwnSort;
import space.smarquardt.sortalgorithm.implementations.impl.Quicksort;

/**
 * Hauptprogramm um Sortieralgorithmen auf den grade der Ordnung zu überprüfen
 *
 * @author Sven Marquardt
 *
 */
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
		// Percentile berechnen
		final Percentile percentile = new Percentile();
		for (final int length : lengthOfArray) {
			/**
			 * Berechne Percentile bis zum 10th Percentile im sortierten array
			 */
			final BiConsumer<String, ListMultimap<Integer, Double>> printPercentile = (name, list) -> {
				final double[] results = ArrayUtils.toPrimitive(list.get(length).stream().sorted()
						.collect(Collectors.toList()).toArray(new Double[list.get(length).size()]));
				System.out.println("Ergebnis " + name + ":" + percentile.evaluate(results, 10.0));
			};
			printPercentile.accept("QuickSort", resultsQuickSort);
			printPercentile.accept("MergeSort", resultsMergeSort);
			printPercentile.accept("OwnSort", resultsOwnSort);
		}
		TestSortAlg.saveResultsToCSV("OwnSort", resultsOwnSort);
		TestSortAlg.saveResultsToCSV("MergeSort", resultsMergeSort);
		TestSortAlg.saveResultsToCSV("QuickSort", resultsQuickSort);

	}

	/**
	 * Speichere die ergebnisse als CSV
	 *
	 * @param sortName
	 *            Name der Algorithmus
	 * @param results
	 *            Resultate
	 */
	private static void saveResultsToCSV(final String sortName, final ListMultimap<Integer, Double> results) {
		final StringBuilder builder = new StringBuilder(results.size());
		for (final Integer key : results.keySet()) {
			builder.append(sortName).append(" Arraylänge:").append(key).append(";");
			results.get(key).stream().sorted().forEach(v -> builder.append(v).append(";"));
			builder.append("\n");
		}
		try {
			Files.write(builder.toString().getBytes(), new File(sortName + ".csv"));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sortiere das übergebene testArray mit dem übergebenen {@link RangedSort}.
	 *
	 * @param length
	 *            Die länge des erstellten testArray
	 * @param testArray
	 *            Testwerte zum sortieren
	 * @param sort
	 *            {@link RangedSort} der das testArray sortieren soll
	 * @return Den Wert berechnet durch
	 *         {@link KendallsCorrelation#correlation(double[], double[])}
	 */
	private static double sortAndPrintResult(final int length, final double[] testArray, final RangedSort sort) {
		sort.sort();
		final double[] resultArray = sort.getSortedArrayTillRange();
		final double result = new KendallsCorrelation().correlation(resultArray, testArray);
		return result;
	}

}
