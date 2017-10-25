/**
 * 
 */
package space.smarquardt.illnes_factorisator.simulation;

/**
 * Teilnehmer bei der Simulation
 * 
 * @author Sven Marquardt
 * @since 25.10.2017
 *
 */
public class Attendee {
	/**
	 * Wahrscheinlichkeit einen anderen {@link Attendee} zu treffen wobei der eigene
	 * {@link Attendee} auch aufgeführt wird
	 */
	private final float[] chancePerAttendee;
	/**
	 * Welche nummer hat dieser Teilnehmer.
	 */
	private int id;


	/**
	 * Erstelle einen Teilnehmer mit seinen Chancen einen anderen Teilnehmer zu
	 * treffen
	 * 
	 * @param chances
	 *            Diesem Teilnehmer zugewiesene Chancen einen anderen Teilnehmer zu
	 *            treffen
	 * @author Sven Marquardt
	 * @since 25.10.2017
	 */
	public Attendee(final float[] chances) {
		this.chancePerAttendee = chances.clone();
	}

	/**
	 * Trifft dieser Teilnehmer den gegebenen Teilnehmer?
	 * 
	 * @param otherId
	 *            Id des anderen Teilnehmers
	 * @return <code>true</code> wenn dieser Teilnehmer den anderen Trifft ansonsten
	 *         <code>false</code>
	 * @author Sven Marquardt
	 * @since 25.10.2017
	 */
	public boolean meetOtherAttende(final int otherId) {
		return Math.random() <= this.chancePerAttendee[otherId];
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

}
