/**
 * 
 */
package space.smarquardt.illnes_factorisator.simulation;

import space.smarquardt.illnes_factorisator.settings.Setting;

/**
 * Simuliert die umgebung mit allen eingetragenen Teilnehmern
 * 
 * @author Sven Marquardt
 * @since 25.10.2017
 *
 */
public class Simulator {
	/**
	 * Anzahl der durchläufe
	 */
	private final int durations;
	/**
	 * Anzahl der teilnehmer
	 */
	private final int attendeesCounter;
	/**
	 * Alle Teilnehmer
	 */
	private final Attendee[] attendees;
	/**
	 * Anzahl der tage pro durchlauf
	 */
	private final int simulationDuration;


	/**
	 * 
	 * @author Sven Marquardt
	 * @since 25.10.2017
	 */
	public Simulator(final Setting setting) {
		super();
		this.durations = setting.getSimulationDuration().orElse(0);
		this.attendeesCounter = setting.getCountPeople().orElse(0);
		this.simulationDuration = setting.getDaysPerSimulation().orElse(0);
		this.attendees = new Attendee[this.attendeesCounter];
	}

	/**
	 * Startet die simulation
	 * 
	 * @author Sven Marquardt
	 * @since 25.10.2017
	 */
	public void startSimulation() {
		for (int i = 0; i < this.durations; i++) {
			for (int j = 0; j < this.simulationDuration; j++) {

			}
		}
	}

}
