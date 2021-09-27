package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherLocationAttributes {
	public Date dataDate;
	public String type;
	@JsonProperty("Location")
	public WeatherDataAttributeLocation weatherDataAttributeLocation;

	@Override
	public String toString() {
		return weatherDataAttributeLocation.toString();
	}
}
