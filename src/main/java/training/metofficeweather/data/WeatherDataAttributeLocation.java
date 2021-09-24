package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataAttributeLocation {
	public String i;
	public String lat;
	public String lon;
	public String name;
	public String country;
	public String continent;
	public String elevation;
	@JsonProperty("Period")
	public List<WeatherDataAtrributePeriod> period;
}
