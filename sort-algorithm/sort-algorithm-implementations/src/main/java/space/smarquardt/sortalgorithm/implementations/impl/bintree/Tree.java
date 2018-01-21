package space.smarquardt.sortalgorithm.implementations.impl.bintree;

/**
 * Ein datenstruktur die werte in einem Baum speichert.
 *
 * @author Sven Marquardt
 *
 */
public interface Tree {
	/**
	 * Einen neuen wert der Datenstruktur anhängen
	 *
	 * @param value
	 *            Wert der in der Baumstruktur gespeichert werden soll
	 */
	public void insert(double value);

	/**
	 * Erhalte alle werte des Baumes sortiert inorder
	 *
	 * @return Array das die werte aufsteigend sortiert enthält
	 */
	public double[] getSorted();

	/**
	 * Erhalte die sortierten werten mit ausnahme des letzen hinzugefügten Wertes
	 *
	 * @return Array mit den Werten aufsteigend sortiert
	 */
	public double[] getSortedWithout();
}
