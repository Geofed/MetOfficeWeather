package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class WeatherLocationAttriubutes {
	public Date dataDate;
	public String type;
	@JsonProperty("Location")
	public WeatherDataAttributeLocation weatherDataAttributeLocation;
}
