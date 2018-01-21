package space.smarquardt.sortalgorithm.implementations;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.randomarraygenerator.RandomArrayGenerator;

import space.smarquardt.sortalgorithm.implementations.impl.OwnSort;

public class TestOwnSort {
	@Test
	public void testOwnSort() {
		final double[] testArray = { 100.0, 50.0, 20.0, 10.0 };
		final RangedSort sort = new OwnSort(300, testArray);
		sort.sort();
		Assertions.assertThat(sort.getResult()).isSorted();
	}

	@Test
	public void testOwnSortRight() {
		final double[] testArray = { 100.0, 250.0, 320.0, 410.0 };
		final RangedSort sort = new OwnSort(300, testArray);
		sort.sort();
		Assertions.assertThat(sort.getResult()).isSorted();
	}

	@Test
	public void testOwnSortRandom() {
		for (int i = 100; i > 0; i--) {
			final double[] testArray = RandomArrayGenerator.generateArray(2000);
			final RangedSort sort = new OwnSort(300, testArray);
			sort.sort();
			Assertions.assertThat(sort.getResult()).isSorted();
			Assertions.assertThat(sort.getResult().length).isEqualTo(testArray.length);
			Assertions.assertThat(sort.getResult()).contains(testArray);
		}
	}

}
