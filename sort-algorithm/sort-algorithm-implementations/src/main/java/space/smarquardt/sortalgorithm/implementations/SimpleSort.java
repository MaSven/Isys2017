package space.smarquardt.sortalgorithm.implementations;

import java.lang.reflect.Array;

/**
 * Einfache Sortierverfahren die ein Array sortieren und dessen Ergebnis
 * liefert.
 *
 * @author Sven Marquardt
 *
 */
public interface SimpleSort {

	/**
	 * Enthält die Werte zum jetzigen zeitpunkt heißt, wenn das Array nicht fertig
	 * Sortiert wurde, sind auch die nicht sortierten Werte in diesem Array
	 * enthalten
	 *
	 * @return Den momentanen Stand der Werte als {@link Array}
	 */
	double[] getResult();

	/**
	 * Name des Algortmus
	 *
	 * @return Liefert den Namen dieses Algorithmus
	 */
	public String nameOfAlgorithm();

	/**
	 * Sortiert das übergeben Array der größe nach
	 *
	 *
	 */
	void sort();

}
