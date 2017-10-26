/**
 * 
 */
package space.smarquardt.illnes_factorisator.simulation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Unbox;

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
	private static final Logger logger = LogManager.getLogger();
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
	 * Benutzt um eine eindutige id für die logs zu erstellen
	 */
	private final long time = System.currentTimeMillis();

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
		Simulator.logger.info("Starte die Simulation Zeit:{}", Unbox.box(System.currentTimeMillis()));
		final List<Attendee> bufferList = new ArrayList<>();
		this.setting.getChances().forEach(row -> {
			final Attendee attendee = new Attendee(
					ArrayUtils.toPrimitive(row.toArray(new Float[this.attendeesCounter])));
			bufferList.add(attendee);
		});
		this.attendees = bufferList.toArray(new Attendee[this.attendeesCounter]);
		Simulator.logger.info("Simulation starten mit {} Teilnehmer", Unbox.box(this.attendees.length));
		// Durchläufe der simulationen
		IntStream.range(1, this.durations).sequential().forEach(i -> {
			Simulator.logger.info("Starte Tag {}", Unbox.box(i));
			this.randomFirst();
			Simulator.logger.info("Ersten random kranken gesetzt");
			try {
				final Path logPath = this.dumpPath(i);
				// Tage
				while (!this.allRecoverd()) {
					Simulator.logger.info("Es gibt {} Kranke",
							Unbox.box(Stream.of(this.attendees).filter(Attendee::isIll).count()));
					Simulator.logger.info("{}",
							Stream.of(this.attendees).map(Attendee::toString).reduce("", String::concat));
					// Tage der Krankheit um einen verringern
					Stream.of(this.attendees).distinct().forEachOrdered(Attendee::decreaseDaysLeftForRecovery);
					/* Einen tag */
					Stream.of(this.attendees).distinct().
					/* Nur kranke bzw ansteckende Leute duerfen mit gesunden Leuten reden */
					filter(Attendee::isIll).filter(Attendee::isContagious).forEachOrdered(attende -> {
						Stream.of(this.attendees).distinct().filter(normalAttende -> !normalAttende.isImmun())
						/* Wenn schon krank ist es egal ob sie sich treffen */
						.filter(normalAttende -> !normalAttende.isIll())
						/* Hat er einen Kranken getroffen ? */
						.filter(att -> attende.meetOtherAttende(att.getId()))
						/* Ist er Krank geworden von der Begegnung? */
						.filter(att -> Math.random() <= 0.1)
						/* Mach ihn krank */
						.forEach(illAttende -> {
							illAttende.setDaysLeftForRecovery(this.simulationDuration);
							illAttende.setIll();
						});
					});
					this.dumpList(i, logPath);

				}
			} catch (final IOException | SimulationException e) {
				Simulator.logger.error(e.getMessage());
			}
			// Neue Krankheitswelle
			this.cleanUp();

		});
	}

	/**
	 * Speichere den zustand alle {@link Attendee}
	 * 
	 * @param dayNumber
	 *            Welchen durchlauf wir haben
	 * @throws SimulationException
	 * @author Sven Marquardt
	 * @since 25.10.2017
	 */
	private void dumpList(final int dayNumber, final Path path) throws SimulationException {
		final StringJoiner joiner = new StringJoiner(";");
		final long directoryTimeId = System.nanoTime();
		Stream.of(this.attendees).forEachOrdered(attendee -> joiner.add(attendee.toString()));
		try {
			Files.write(Paths.get(path.toString(), "Simulations_Durchlauf" + dayNumber + "" + directoryTimeId + ".log"),
					joiner.toString().getBytes(), StandardOpenOption.CREATE);
		} catch (final IOException e) {
			throw new SimulationException("Schreiben der Log datei fehlgeschlagen", e);
		}
	}

	private Path dumpPath(final int dayNumber) throws IOException {
		final Path path = Paths.get(this.setting.getPathToDumps(), "Durchlauf" + this.time + "Tag" + dayNumber);
		return Files.createDirectory(path);
	}

	/**
	 * Alle {@link Attendee} wieder gesund fuer naechste Simulation
	 * 
	 * @author Sven Marquardt
	 * @since 25.10.2017
	 */
	private void cleanUp() {
		Stream.of(this.attendees).forEach(attendee -> {
			attendee.setRecoverd();
			attendee.setImmun(false);
		});
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
		// Plus 1 da dieser tag am anfang der schleife wieder abgezogen wird
		this.attendees[randomAttende].setDaysLeftForRecovery(this.simulationDuration + 1);
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
		return !Stream.of(this.attendees).anyMatch(Attendee::isIll);
	}

}
