/**
 * 
 */
package space.smarquardt.illnes_factorisator.simulation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;

import space.smarquardt.illnes_factorisator.exception.IllnesFactorisatoSettingException;
import space.smarquardt.illnes_factorisator.exception.SimulationException;
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
	private Attendee[] attendees;
	/**
	 * Anzahl der tage pro durchlauf
	 */
	private final int simulationDuration;
	/**
	 * Einstellungen fuer den Simulator
	 */
	private final Setting setting;

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
		this.setting = setting;
	}

	/**
	 * Startet die simulation
	 * 
	 * @author Sven Marquardt
	 * @throws IllnesFactorisatoSettingException
	 * @since 25.10.2017
	 */
	public void startSimulation() throws IllnesFactorisatoSettingException {
		final List<Attendee> bufferList = new ArrayList<>();
		this.setting.getChances().forEach(row -> {
			final Attendee attendee = new Attendee(
					ArrayUtils.toPrimitive(row.toArray(new Float[this.attendeesCounter])));
			bufferList.add(attendee);
		});

		this.attendees = bufferList.toArray(new Attendee[this.attendeesCounter]);
		// Durchläufe der simulationen
		IntStream.range(0, this.durations).forEach((i) -> {
			this.randomFirst();
			while (!this.allRecoverd()) {
				/* Einen tag */
				Stream.of(this.attendees).
				/* Nur kranke Leute duerfen mit gesunden Leuten reden */
				filter(Attendee::isIll).
				/* Wenn krank einen tag näher an der genesung */
				map(illAttende -> {
					illAttende.decreaseDaysLeftForRecovery();
					return illAttende;
				})
				.forEachOrdered(attende -> Stream.of(this.attendees)
						/* Hat er einen Kranken getroffen ? */
						.filter(att -> attende.meetOtherAttende(att.getId()))
								/* Ist er Krank geworden von der Begegnung? */
						.filter(att -> Math.random() <= 0.1)
						/* Mach ihn krank */
						.forEach(illAttende -> {
							illAttende.setDaysLeftForRecovery(this.simulationDuration);
							illAttende.setIll();
						}));

			}
			// Neue Krankheitswelle
			this.cleanUp();
			try {
				this.dumpList(i);
			} catch (final SimulationException e) {
				System.err.println(e.getMessage());
			}
		});
	}



	private void dumpList(final int dayNumber) throws SimulationException {
		final StringJoiner joiner = new StringJoiner(";");
		Stream.of(this.attendees).forEachOrdered(attendee -> joiner.add(attendee.toString()));
		try {
			Files.write(Paths.get(this.setting.getPathToDumps(), "tag" + dayNumber + ".log"),
					joiner.toString().getBytes(), StandardOpenOption.CREATE);
		} catch (final IOException e) {
			throw new SimulationException("Schreiben der Log datei fehlgeschlagen", e);
		}
	}

	/**
	 * Alle {@link Attendee} wieder gesund fuer naechste Simulation
	 * 
	 * @author Sven Marquardt
	 * @since 25.10.2017
	 */
	private void cleanUp() {
		Stream.of(this.attendees).forEach(Attendee::setRecoverd);
	}

	/**
	 * Lässt einen {@link Attendee} erkranken
	 * 
	 * @author Sven Marquardt
	 * @since 25.10.2017
	 */
	private void randomFirst() {
		final int randomAttende = RandomUtils.nextInt(0, this.attendeesCounter);
		this.attendees[randomAttende].setIll();
	}

	/**
	 * Gibt an, ob alle {@link Attendee} wieder gesund sind
	 * 
	 * @return <code>true</code> wenn alle {@link Attendee} wieder gesund sind,
	 *         ansonsten <code>false</code>
	 * @author Sven Marquardt
	 * @since 25.10.2017
	 */
	private boolean allRecoverd() {
		return Stream.of(this.attendees).anyMatch(Attendee::isIll);
	}

}
