package space.smarquardt.sortalgorithm.implementations;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.randomarraygenerator.RandomArrayGenerator;

import space.smarquardt.sortalgorithm.implementations.impl.Quicksort;

public class TestQuickSort {
	@Test
	public void quickSortTest() {
		final double[] testArray = RandomArrayGenerator.generateArray(10000);
		final SimpleSort sort = new Quicksort(0, testArray);
		sort.sort();
		Assertions.assertThat(sort.getResult()).isSorted();
	}

	@Test
	public void simpleQuickSortTest() {
		final double[] testArray = { 0, 1, 2, 3, 3, 4, 5 };
		final SimpleSort sort = new Quicksort(0, testArray);
		sort.sort();
		Assertions.assertThat(sort.getResult()).isSorted();
	}

}
