package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherDataAttributes {
	public String name;
	public String units;
	@JsonProperty("$")
	public String description;
}
