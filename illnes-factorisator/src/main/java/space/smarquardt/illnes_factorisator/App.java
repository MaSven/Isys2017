package space.smarquardt.illnes_factorisator;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.status.StatusLogger;

import space.smarquardt.illnes_factorisator.exception.IllnesFactorisatoSettingException;
import space.smarquardt.illnes_factorisator.settings.Setting;
import space.smarquardt.illnes_factorisator.simulation.Simulator;

/**
 * Hello world!
 *
 */
public class App
{
	public static void main(final String[] args)
	{
		StatusLogger.getLogger().setLevel(Level.ALL);
		Setting setting = null;
		if (ArrayUtils.isEmpty(args)) {
			// Einstellungen sollten durch das Maven plugin erfolgt sein
			setting = new Setting();
		} else {
			try {
				setting = new Setting(new URI(args[0]));
			} catch (IllnesFactorisatoSettingException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		final Simulator simulator = new Simulator(setting);
		try {
			simulator.startSimulation();
		} catch (final IllnesFactorisatoSettingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
