package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationsRoot {
	@JsonProperty("Locations")
	public LocationsList locations;

	@Override
	public String toString() {
		return "Root{" +
				"locations=" + locations +
				'}';
	}
}
