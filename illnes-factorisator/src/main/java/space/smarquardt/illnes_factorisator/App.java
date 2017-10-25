package space.smarquardt.illnes_factorisator;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.ArrayUtils;

import space.smarquardt.illnes_factorisator.exception.IllnesFactorisatoSettingException;
import space.smarquardt.illnes_factorisator.settings.Setting;

/**
 * Hello world!
 *
 */
public class App
{
	public static void main(final String[] args) throws IllnesFactorisatoSettingException, URISyntaxException
	{
		Setting setting;
		if (ArrayUtils.isEmpty(args)) {
			// Einstellungen sollten durch das Maven plugin erfolgt sein
			setting = new Setting();
		} else {
			setting = new Setting(new URI(args[0]));
		}

	}
}
