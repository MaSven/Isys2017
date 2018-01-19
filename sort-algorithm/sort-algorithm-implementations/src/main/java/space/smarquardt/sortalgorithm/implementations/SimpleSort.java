package space.smarquardt.sortalgorithm.implementations;

public interface SimpleSort {

	/**
	 * Erhalte das fertig sortierte array
	 *
	 * @return ein sortiertes array
	 */
	double[] getResult();

	/**
	 * Name des Algortmus
	 * 
	 * @return
	 */
	public String nameOfAlgorithm();

	/**
	 * Sortiert das übergeben Array der größe nach
	 *
	 * @return Array mit den werten sortiert in aufsteigender form
	 */
	void sort();

}
