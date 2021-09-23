package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Root {
	@JsonProperty("Locations")
	public Locations locations;
}
