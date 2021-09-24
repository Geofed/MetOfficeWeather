package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataAttributes {
	public String name;
	public String units;
	@JsonProperty("$")
	public String description;

	@Override
	public String toString() {
		return  units + "\n" + description;
	}
}
