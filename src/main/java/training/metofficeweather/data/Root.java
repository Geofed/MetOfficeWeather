package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Root {
	@JsonProperty("Locations")
	public LocationsList locations;

	@Override
	public String toString() {
		return "Root{" +
				"locations=" + locations +
				'}';
	}
}
