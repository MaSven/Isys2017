/**
 *
 */
package space.smarquardt.sortalgorithm.implementations.impl;

/**
 * Implementierung von Quicksort nach
 * {@linkplain http://www.vogella.com/tutorials/JavaAlgorithmsQuicksort/article.html}
 *
 * @author Sven Marquardt
 *
 */
public class Quicksort extends AbstractSort {

	/**
	 * @param cylces
	 * @param data
	 */
	public Quicksort(final int cylces, final double[] data) {
		super(cylces, data);
	}

	private void divide(final int left, final int right) {
		int i = left;
		int j = right;
		final double pivot = this.data[left + ((right - left) / 2)];
		while (i <= j) {
			while (this.isLessThan.test(this.data[i], pivot)) {
				i++;
			}
			while (this.isGreaterThan.test(this.data[j], pivot)) {
				j--;
			}

			if (i <= j) {
				this.swap(i, j);
				i++;
				j--;
			}
		}
		if (left < j) {
			this.divide(left, j);
		}
		if (i < right) {
			this.divide(i, right);
		}
	}

	@Override
	public double[] getResult() {
		return this.data.clone();
	}

	@Override
	public String nameOfAlgorithm() {
		return "QuickSort";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see space.smarquardt.sortalgorithm.implementations.impl.AbstractSort#sort()
	 */
	@Override
	public void sort() {
		this.divide(0, this.data.length - 1);
	}

	private void swap(final int i, final int j) {
		final double temp = this.data[i];
		this.data[i] = this.data[j];
		this.data[j] = temp;

	}

}
