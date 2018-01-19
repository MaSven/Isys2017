package space.smarquardt.sortalgorithm.implementations;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.randomarraygenerator.RandomArrayGenerator;

import space.smarquardt.sortalgorithm.implementations.impl.OwnSort;

public class TestOwnSort {
	@Test
	public void testOwnSort() {
		final double[] testArray = RandomArrayGenerator.generateArray(120);
		final RangedSort sort = new OwnSort(300, testArray);
		sort.sort();
		Assertions.assertThat(sort.getResult()).isSorted();
	}

}
