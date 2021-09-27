package training.metofficeweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import training.metofficeweather.data.Locations;
import training.metofficeweather.data.LocationsList;
import training.metofficeweather.data.LocationsRoot;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SiteInfo {
	private List<Locations> locations;

	@GetMapping
	public List<Locations> getLocations() {
		return locations;
	}

	public void setLocations(List<Locations> locations) {
		this.locations = locations;
	}

	public SiteInfo(List<Locations> initLocations) {
		locations = initLocations;
	}
}
