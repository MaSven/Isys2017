package space.smarquardt.sortalgorithm.implementations.impl;

/**
 * Mergesort implementation nach
 * {@linkplain https://en.wikipedia.org/wiki/Merge_sort#Top-down_implementation}
 *
 * @author Sven Marquardt
 *
 */
public final class Mergesort extends AbstractSort {
	/**
	 * Kopie der Daten die zu sortieren sind. Enthält auch das am ende sortierte
	 * array
	 */
	private final double[] copyData;

	/**
	 * @param cylces
	 * @param data
	 */
	public Mergesort(final int cylces, final double[] data) {
		super(cylces, data);
		this.copyData = data.clone();
	}

	@Override
	public double[] getResult() {
		return this.data.clone();
	}

	@Override
	public String nameOfAlgorithm() {
		return "MergeSort";
	}

	@Override
	public void sort() {
		this.topDownSplitMerge(this.copyData, 0, this.data.length, this.data);

	}

	/**
	 * Vergleichen der Elemente und sortieren an dem richtigen platz
	 *
	 * @param arrayB
	 *            Array in dem die sortierten Elemente gespeichert werden
	 * @param begin
	 *            Anfang der suche
	 * @param middle
	 *            Mitte der suche
	 * @param end
	 *            Ende der suche
	 * @param arrayA
	 *            Array mit den daten
	 */
	private void topDownMerge(final double[] arrayA, final int begin, final int middle, final int end,
			final double[] arrayB) {
		int i = begin;
		int j = middle;
		for (int k = begin; k < end; k++) {
			if ((i < middle) && ((j >= end) || this.isEqualsOrLessThan.test(arrayA[i], arrayA[j]))) {
				arrayB[k] = arrayA[i];
				i++;
			} else {
				arrayB[k] = arrayA[j];
				j++;
			}
		}
	}

	/**
	 * Splitten der Arrays in paare <b>rekursiv</b>
	 *
	 * @param arrayB
	 *            Array in dem die sortierung gespeichert wird
	 * @param begin
	 *            Anfang der suche index inklusiv
	 * @param end
	 *            Ende der suche index exklusiv
	 * @param arrayA
	 *            Array in dem die daten zur sortierung beinhaltet sind
	 */
	private void topDownSplitMerge(final double[] arrayB, final int begin, final int end, final double[] arrayA) {
		if ((end - begin) < 2) {
			return;
		}
		final int middle = (begin + end) / 2;
		this.topDownSplitMerge(arrayA, begin, middle, arrayB);
		this.topDownSplitMerge(arrayA, middle, end, arrayB);
		this.topDownMerge(arrayB, begin, middle, end, arrayA);

	}

}
