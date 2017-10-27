/**
 * 
 */
package space.smarquardt.illnes_factorisator.simulation;

import java.util.Arrays;
import java.util.Random;

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
	private final int id;
	/**
	 * Ist ein Teilnehme krank
	 */
	private boolean isIll = false;
	/**
	 * Wurde dieser Teilnehmer geimpft
	 */
	private boolean isImmun = false;
	/**
	 * Tage bis Teilnehmer wieder gesund ist
	 */
	private int daysLeftForRecovery;

	/**
	 * Anzahl der Attendes
	 */
	private static int countAttendes = 0;
	
	private static Random random = new Random(12l);

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
		this.id = Attendee.countAttendes++;
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
		return random.nextDouble() < this.chancePerAttendee[otherId];
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @return the isIll
	 */
	public boolean isIll() {
		return this.isIll;
	}

	public boolean isContagious() {
		return this.daysLeftForRecovery <= 9;
	}

	/**
	 * @param isIll
	 *            the isIll to set
	 */
	public void setIll() {
		this.isIll = true;
	}

	/**
	 * @return the isImmun
	 */
	public boolean isImmun() {
		return this.isImmun;
	}

	/**
	 * @param isImmun
	 *            the isImmun to set
	 */
	public void setImmun(final boolean value) {
		this.isImmun = value;
	}

	/**
	 * @return the daysLeftForRecovery
	 */
	public int getDaysLeftForRecovery() {
		return this.daysLeftForRecovery;
	}

	/**
	 * @param daysLeftForRecovery
	 *            the daysLeftForRecovery to set
	 */
	public void setDaysLeftForRecovery(final int daysLeftForRecovery) {
		this.daysLeftForRecovery = daysLeftForRecovery;
	}

	/**
	 * {@link Attendee} ist nicht mehr Krank
	 * 
	 * @author Sven Marquardt
	 * @since 25.10.2017
	 */
	public void setRecoverd() {
		this.isIll = false;
	}

	/**
	 * Dekrementiere tage bis zur genesung um einen. Wenn keine tage mehr übrig ist
	 * er gesund
	 * 
	 * @author Sven Marquardt
	 * @since 25.10.2017
	 */
	public void decreaseDaysLeftForRecovery() {
		if (this.isIll) {
			if (this.daysLeftForRecovery > 1) {
				this.daysLeftForRecovery--;
			} else {
				this.daysLeftForRecovery--;
				this.setRecoverd();
				this.setImmun(true);
			}
		}

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
		result = prime * result + Arrays.hashCode(this.chancePerAttendee);
		result = prime * result + this.daysLeftForRecovery;
		result = prime * result + this.id;
		result = prime * result + (this.isIll ? 1231 : 1237);
		result = prime * result + (this.isImmun ? 1231 : 1237);
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
		if (!(obj instanceof Attendee)) {
			return false;
		}
		final Attendee other = (Attendee) obj;
		if (!Arrays.equals(this.chancePerAttendee, other.chancePerAttendee)) {
			return false;
		}
		if (this.daysLeftForRecovery != other.daysLeftForRecovery) {
			return false;
		}
		if (this.id != other.id) {
			return false;
		}
		if (this.isIll != other.isIll) {
			return false;
		}
		if (this.isImmun != other.isImmun) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (this.isIll) {
			return "K";
		} else if (this.isImmun) {
			return "I";
		}
		return "O";
	}

}
