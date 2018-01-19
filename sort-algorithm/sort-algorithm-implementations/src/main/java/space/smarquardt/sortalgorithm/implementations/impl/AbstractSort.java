/**
 *
 */
package space.smarquardt.sortalgorithm.implementations.impl;

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
	 * Anzahl der vergleiche die durchführt dürfen werden
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

}
