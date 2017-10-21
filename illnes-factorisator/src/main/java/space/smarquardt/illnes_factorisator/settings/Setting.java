package space.smarquardt.illnes_factorisator.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.lang3.math.NumberUtils;

import space.smarquardt.illnes_factorisator.exception.IllnesFactorisatoSettingException;

/**
 * 
 * @author sven
 *
 */
public class Setting {
	/**
	 * {@link Properties} key fuer die Simulations dauer
	 */
	private static final String SIMULATION_DURATION_KEY = "Simulation.Dauer";
	/**
	 * {@link Properties} key fuer die anzal der Menschen die teilnimmt
	 */
	private static final String COUNT_PEOPLE_KEY = "Anzahl.Teilnehmer";
	/**
	 * {@link Properties} key fuer die Begegnungswahrscheinlichkeiten
	 */
	private static final String PEOPLE_MEET_CHANCE = "Datei.Begegnungswahrscheinlichkeiten";
	/**
	 * Anzahl der Personen die geprueft werden soll
	 */
	private final Optional<Integer> countPeople;
	/**
	 * Anzahl der Tage die, die Simulation laufen soll
	 */
	private final Optional<Integer> simulationDuration;
	/**
	 * Datei die die infos enthält bei welcher Wahrscheinlichkeit sich die
	 * Teilnehmer treffen sollen.
	 */
	private final File dirToPeopleMeetChance;

	/**
	 * Konsturktur um die Properties selber zu setzen duch das angeben einer Datei
	 * 
	 * @param fileDir
	 *            Pfad zu {@link Properties} datei
	 * @author sven
	 * @throws IllnesFactorisatoSettingException
	 *             Wenn die angegebene Propertie datei nicht gefunden werden konnte
	 * @since 22.10.2017
	 */
	public Setting(final URI fileDir) throws IllnesFactorisatoSettingException {
		final File propertieFile = new File(fileDir);
		if (propertieFile.exists()) {
			final Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(propertieFile));
				this.countPeople = this.getNumberOfPropertie(properties, Setting.COUNT_PEOPLE_KEY);
				this.simulationDuration = this.getNumberOfPropertie(properties, Setting.SIMULATION_DURATION_KEY);
				final String bufferDir = properties.getProperty(Setting.PEOPLE_MEET_CHANCE);
				this.dirToPeopleMeetChance = new File(bufferDir);
			} catch (final IOException e) {
				throw new IllnesFactorisatoSettingException(
						"Proertie Datei konnte nicht gelesen werden. Datei:" + propertieFile.getAbsolutePath());
			}
		} else {
			throw new IllnesFactorisatoSettingException("Propertie Datei konnte nicht gefunden werden.");
		}
	}

	/**
	 * Wenn die Properties im {@link System#getProperties()} enthalten ist.
	 * 
	 * @author sven
	 * @since 22.10.2017
	 */
	public Setting() {
		final Properties properties = System.getProperties();
		this.countPeople = this.getNumberOfPropertie(properties, Setting.COUNT_PEOPLE_KEY);
		this.simulationDuration = this.getNumberOfPropertie(properties, Setting.SIMULATION_DURATION_KEY);
		this.dirToPeopleMeetChance = new File(properties.getProperty(Setting.PEOPLE_MEET_CHANCE));
	}

	/**
	 * Extrahiert aus einem String den passenden {@link Integer} wert
	 * 
	 * @param properties
	 *            {@link Properties} aus dem der String extrahiert wird
	 * @param key
	 *            Key mit dem der String gesucht wird
	 * @return {@link Optional} mit dem Inhalt aus der Propertie oder nichts wenn es
	 *         keine gueltige Zahl war
	 * @author sven
	 * @since 22.10.2017
	 */
	private Optional<Integer> getNumberOfPropertie(final Properties properties, final String key) {
		final String buffer = properties.getProperty(key);
		if (NumberUtils.isCreatable(buffer)) {
			return Optional.of(NumberUtils.createNumber(buffer).intValue());
		}
		return Optional.empty();
	}


	/**
	 * @return the countPeople
	 */
	public Optional<Integer> getCountPeople() {
		return this.countPeople;
	}

	/**
	 * @return the countDays
	 */
	public Optional<Integer> getCountDays() {
		return this.simulationDuration;
	}

	/**
	 * @return the dirToPeopleMeetChance
	 */
	public File getDirToPeopleMeetChance() {
		return this.dirToPeopleMeetChance;
	}







}
