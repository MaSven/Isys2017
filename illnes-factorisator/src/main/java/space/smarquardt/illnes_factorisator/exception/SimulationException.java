/**
 * 
 */
package space.smarquardt.illnes_factorisator.exception;

/**
 * @author Sven Marquardt
 * @since 25.10.2017
 *
 */
public class SimulationException extends Exception {

	/**
	 * 
	 * @author Sven Marquardt
	 * @since 25.10.2017
	 */
	public SimulationException(final String message) {
		super(message);
	}

	/**
	 * 
	 * @author Sven Marquardt
	 * @since 25.10.2017
	 */
	public SimulationException(final String message, final Throwable t) {
		super(message, t);
	}
}
