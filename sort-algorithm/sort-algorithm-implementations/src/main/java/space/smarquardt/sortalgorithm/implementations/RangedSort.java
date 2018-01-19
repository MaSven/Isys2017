package space.smarquardt.sortalgorithm.implementations;

public interface RangedSort extends SimpleSort {

	/**
	 * Erhalte das sortierte array zum stand der erreichten
	 *
	 * @return Array mit double das nicht komplett sortiert sein muss
	 */
	double[] getSortedArrayTillRange();
}
