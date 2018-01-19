package space.smarquardt.sortalgorithm.implementations;

import java.util.Arrays;

import org.assertj.core.api.AbstractDoubleArrayAssert;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.randomarraygenerator.RandomArrayGenerator;

import space.smarquardt.sortalgorithm.implementations.impl.Mergesort;

/**
 * Einfacher test für die funktionalität von {@link #MergeSort()}. Sortierung
 * wird mittels {@link AbstractDoubleArrayAssert#isSorted()} geprüft.
 *
 * @author Sven Marquardt
 *
 */
public class TestMergeSort {

	/**
	 * Testen mit wirklichen ranomd daten
	 */
	@Test
	public void testMergeSortRandom() {
		final double[] testArray = RandomArrayGenerator.generateArray(10);
		final double[] checkArray = testArray.clone();
		Arrays.sort(checkArray);
		final RangedSort sort = new Mergesort(0, testArray);
		sort.sort();
		final double[] resultArray = sort.getResult();
		Assertions.assertThat(resultArray).isSorted();
		final double[] rangeResult = sort.getSortedArrayTillRange();
		Assertions.assertThat(rangeResult).isNotNull();
	}

	/**
	 * Sollte keine Elemente vertauschen da schon sortiert
	 */
	@Test
	public void testMergeSortSimple() {

		final double[] testArray = { 1.0, 2.0, 3.0, 4.0 };
		System.out.println(Arrays.toString(testArray));
		final SimpleSort sort = new Mergesort(0, testArray);
		sort.sort();
		final double[] resultArray = sort.getResult();
		System.out.println(Arrays.toString(testArray));
		System.out.println(Arrays.toString(resultArray));
		Assertions.assertThat(resultArray).isEqualTo(testArray);
	}

}
