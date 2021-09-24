package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonProperty;

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
