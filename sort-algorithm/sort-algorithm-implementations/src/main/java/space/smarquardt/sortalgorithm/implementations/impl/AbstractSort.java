/**
 *
 */
package space.smarquardt.sortalgorithm.implementations.impl;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;

import space.smarquardt.sortalgorithm.implementations.RangedSort;

/**
 * Baseklasse für Sortieralgorithmen. Zu sortierende Daten sind in {@link #data}
 * zu finden.
 *
 * @author Sven Marquardt
 *
 */
public abstract class AbstractSort implements RangedSort, DoubleComparator {
	/**
	 * Anzahl der vergleiche die durchgeführt werden dürfen
	 */
	int cylces;
	/**
	 * Enthält die daten die zu sortieren sind
	 */
	double[] data;
	/**
	 * Zaehler um die anzahl der vergleiche zu zaehlen
	 */
	AtomicInteger counter = new AtomicInteger(0);

	/**
	 * Enthält die daten, die angeordnet waren nach dem der Counter erreicht wurde.
	 */
	private double[] result;

	final BiPredicate<Double, Double> isLessThan = (numberOne, numberTwo) -> {
		this.incCounter();
		return numberOne < numberTwo;
	};

	final BiPredicate<Double, Double> isGreaterThan = (numberOne, numberTwo) -> {
		this.incCounter();
		return numberOne > numberTwo;
	};

	final BiPredicate<Double, Double> isEquals = (numberOne, numberTwo) -> {
		this.incCounter();
		return numberOne == numberTwo;
	};

	final BiPredicate<Double, Double> isEqualsOrLessThan = (numberOne, numberTwo) -> {
		// Weil es ein vergleich ist und nicht 2
		this.counter.decrementAndGet();
		return this.isLessThan.or(this.isEquals).test(numberOne, numberTwo);
	};

	final BiPredicate<Double, Double> isEqualsOrGreaterThan = (numberOne, numberTwo) -> {
		// Weil es ein vergleich ist und nicht 2
		this.counter.decrementAndGet();
		return this.isGreaterThan.or(this.isEquals).test(numberOne, numberTwo);
	};

	/**
	 * Erstelle einen Sort. Art der sortierung hängt von der Kindklasse ab
	 *
	 * @param cylces
	 *            Anzahl der vergleichsoperationen die durchgeführt werden dürfen
	 * @param data
	 *            Daten die zu Sortieren sind
	 */
	public AbstractSort(final int cylces, final double[] data) {
		super();
		this.cylces = cylces;
		this.data = data.clone();
	}

	@Override
	public abstract double[] getResult();

	@Override
	public final double[] getSortedArrayTillRange() {
		return this.result;
	}

	/**
	 * Es wurde ein Vergleich durchgeführt. Speichert momentanen Stand der
	 * Sortierung und inkrementiert den Vergleichzähler um einen
	 */
	private void incCounter() {
		if (this.counter.intValue() <= this.cylces) {
			this.result = this.getResult();
		}
		this.counter.incrementAndGet();
	}

	@Override
	public abstract String nameOfAlgorithm();

	/*
	 * (non-Javadoc)
	 *
	 * @see org.sort.algorithm.implementations.SimpleSort#sort(double[], int)
	 */
	@Override
	public abstract void sort();

	/*
	 * (non-Javadoc)
	 *
	 * @see space.smarquardt.sortalgorithm.implementations.impl.DoubleComparator#
	 * isLessThan(double, double)
	 */
	@Override
	public boolean isLessThan(final double firstNumber, final double secondNumber) {
		return this.isLessThan.test(firstNumber, secondNumber);
	}

	/**
	 * Sind die anzahl der Vergleichoperatoren ausgeschöpft
	 *
	 * @return true wenn die Anzahl der vergleiche ausgeschöpft ist
	 */
	boolean isCycleExceeded() {
		return !(this.counter.get() < this.cylces);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.counter == null) ? 0 : this.counter.hashCode());
		result = (prime * result) + this.cylces;
		result = (prime * result) + Arrays.hashCode(this.data);
		result = (prime * result) + ((this.isEquals == null) ? 0 : this.isEquals.hashCode());
		result = (prime * result) + ((this.isEqualsOrGreaterThan == null) ? 0 : this.isEqualsOrGreaterThan.hashCode());
		result = (prime * result) + ((this.isEqualsOrLessThan == null) ? 0 : this.isEqualsOrLessThan.hashCode());
		result = (prime * result) + ((this.isGreaterThan == null) ? 0 : this.isGreaterThan.hashCode());
		result = (prime * result) + ((this.isLessThan == null) ? 0 : this.isLessThan.hashCode());
		result = (prime * result) + Arrays.hashCode(this.result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AbstractSort)) {
			return false;
		}
		final AbstractSort other = (AbstractSort) obj;
		if (this.counter == null) {
			if (other.counter != null) {
				return false;
			}
		} else if (!this.counter.equals(other.counter)) {
			return false;
		}
		if (this.cylces != other.cylces) {
			return false;
		}
		if (!Arrays.equals(this.data, other.data)) {
			return false;
		}
		if (this.isEquals == null) {
			if (other.isEquals != null) {
				return false;
			}
		} else if (!this.isEquals.equals(other.isEquals)) {
			return false;
		}
		if (this.isEqualsOrGreaterThan == null) {
			if (other.isEqualsOrGreaterThan != null) {
				return false;
			}
		} else if (!this.isEqualsOrGreaterThan.equals(other.isEqualsOrGreaterThan)) {
			return false;
		}
		if (this.isEqualsOrLessThan == null) {
			if (other.isEqualsOrLessThan != null) {
				return false;
			}
		} else if (!this.isEqualsOrLessThan.equals(other.isEqualsOrLessThan)) {
			return false;
		}
		if (this.isGreaterThan == null) {
			if (other.isGreaterThan != null) {
				return false;
			}
		} else if (!this.isGreaterThan.equals(other.isGreaterThan)) {
			return false;
		}
		if (this.isLessThan == null) {
			if (other.isLessThan != null) {
				return false;
			}
		} else if (!this.isLessThan.equals(other.isLessThan)) {
			return false;
		}
		if (!Arrays.equals(this.result, other.result)) {
			return false;
		}
		return true;
	}

}
