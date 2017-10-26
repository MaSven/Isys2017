package space.smarquardt.illnes_factorisator;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.status.StatusLogger;

import space.smarquardt.illnes_factorisator.settings.Setting;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(final String[] args) throws IOException {
		StatusLogger.getLogger().setLevel(Level.ALL);
		final Setting setting = null;
		// if (ArrayUtils.isEmpty(args)) {
		// // Einstellungen sollten durch das Maven plugin erfolgt sein
		// setting = new Setting();
		// } else {
		// try {
		// setting = new Setting(new URI(args[0]));
		// } catch (IllnesFactorisatoSettingException | URISyntaxException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//
		// final Simulator simulator = new Simulator(setting);
		// try {
		// simulator.startSimulation();
		// } catch (final IllnesFactorisatoSettingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		App.compute();

	}

	private static void compute() throws IOException {
		final Set<OutCome> outComes = new LinkedHashSet<>();
		final Map<String, List<Path>> directorys = new LinkedHashMap<>();
		Files.walk(Paths.get("/run/media/sven/Backup/log"), FileVisitOption.FOLLOW_LINKS)
		.forEach(path -> {
			if (path.toFile().isFile()) {
				List<Path> buffer = directorys.get(path.getParent().toString());
				if (buffer == null) {
					buffer = new ArrayList<>();
				}
				buffer.add(path);
				directorys.put(path.getParent().toString(), buffer);
			}
		});
		directorys.entrySet().stream().forEachOrdered(entry -> {
			final OutCome outCome = new OutCome();
			final List<String> lines = new ArrayList<>();
			entry.getValue().forEach(file -> {
				try {
					lines.addAll(Files.readAllLines(file));

				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			// ANzahl der Dateien sind Tage
			outCome.days = entry.getValue().size();
			lines.forEach(line -> {
				final int illCounter = StringUtils.countMatches(line, "K");
				if (illCounter > outCome.ill) {
					outCome.ill = illCounter;
				}
			});
			outComes.add(outCome);
		});
		final Map<Integer, Integer> illCounter = new LinkedHashMap<>();
		final Map<Integer, Integer> dayCounter = new LinkedHashMap<>();
		for (final OutCome come : outComes) {
			if (illCounter.containsKey(come.ill)) {
				illCounter.put(come.ill, illCounter.get(come.ill) + 1);
			} else {
				illCounter.put(come.ill, 1);
			}
			if (dayCounter.containsKey(come.days)) {
				dayCounter.put(come.days, dayCounter.get(come.days) + 1);
			} else {
				dayCounter.put(come.days, 1);
			}
		}
		System.out.println("Kranke");
		illCounter.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + ";" + entry.getValue() + ";" + (double) entry.getValue() / 10_000);
		});
		System.out.println("Tage");
		dayCounter.entrySet()
				.forEach(entry -> System.out
						.println(entry.getKey() + ";" + entry.getValue() + ";" + (double) entry.getValue() / 10_000));
		System.out.println("Im schnitt dauerte eine Krankheitswelle ");

	}

	private static class OutCome {
		public int ill = 0;
		public int days = 0;


	}
}
