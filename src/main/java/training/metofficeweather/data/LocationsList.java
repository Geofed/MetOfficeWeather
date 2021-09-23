package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LocationsList {
	@JsonProperty("Location")
	public List<Locations> location;
}
