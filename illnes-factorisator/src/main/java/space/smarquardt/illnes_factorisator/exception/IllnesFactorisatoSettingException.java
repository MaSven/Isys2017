package space.smarquardt.illnes_factorisator.exception;

/**
 * Exception fuer die Einstellung des Programmes
 * 
 * @author sven
 * @since 22.10.2017
 *
 */
public class IllnesFactorisatoSettingException extends Exception {

	/**
	 * Exception mit nachricht und stacktrace
	 * 
	 * @param message
	 *            Nachricht die am oberen ende des Stacktraces angezeigt wird
	 * @param t
	 *            Grund der Exception
	 */
	public IllnesFactorisatoSettingException(final String message, final Throwable t) {
		super(message, t);
	}

	/**
	 * Exception nur mit der Nachricht
	 * 
	 * @param message
	 *            Nachricht die am oberen ende des stacktraces angezeigt werden soll
	 * @author sven
	 * @since 22.10.2017
	 */
	public IllnesFactorisatoSettingException(final String message) {
		super(message);
	}

}
