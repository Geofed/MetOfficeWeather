package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationsList {
	@JsonProperty("Location")
	public List<Locations> location;

	@Override
	public String toString() {
		return "LocationsList{" + "location=" + location +
				'}';
	}
}
