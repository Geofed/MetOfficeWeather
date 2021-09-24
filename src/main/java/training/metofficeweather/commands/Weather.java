package training.metofficeweather.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import training.metofficeweather.Command;
import training.metofficeweather.data.Locations;
import training.metofficeweather.data.LocationsRoot;
import training.metofficeweather.data.WeatherRoot;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import static training.metofficeweather.Main.localFlag;

public class Weather implements Command {
	@Override
	public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
		String locationId = null;
		try {
			locationId = locationsHashMap.get(input.toUpperCase(Locale.ROOT)).id;
		}
		catch (Exception e) {
			System.out.printf("Could not find %s\n", input);
			return;
		}
		if (localFlag) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				WeatherRoot out = mapper.readValue(Paths.get("Data/" + locationId + ".json").toFile(), WeatherRoot.class);
				System.out.println(out);
			} catch (Exception e) {
				System.out.println("Unable to locate local weather data");
			}
		} else {
			try {
				URL url = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/"+locationId+"?res=3hourly&key=" + System.getenv("API_KEY"));
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestProperty("accept", "application/json");
				InputStream responseStream = connection.getInputStream();
				ObjectMapper mapper = new ObjectMapper();
				Object out = mapper.readValue(responseStream, WeatherRoot.class);
				System.out.println(out);
			}
			catch (Exception e) {
				System.out.println("Unable to process web request");
			}
		}
	}
}
