package training.metofficeweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import training.metofficeweather.data.Locations;
import training.metofficeweather.data.LocationsRoot;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class WeatherCommand implements Command {
	@Override
	public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
		String locationId = locationsHashMap.get(input).id;
		try {
			URL url = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/"+locationId+"?res=3hourly&key=" + System.getenv("API_KEY"));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("accept", "application/json");
			InputStream responseStream = connection.getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			Object out = mapper.readValue(responseStream, LocationsRoot.class);
		}
		catch (Exception e) {
			System.out.println("Unable to process web request");
		}
	}
}
