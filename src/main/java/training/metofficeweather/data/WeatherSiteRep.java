package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherSiteRep {
	@JsonProperty("Wx")
	public WeatherDataAttributes dataAttributes;

	@JsonProperty("DV")
	public WeatherLocationAttriubutes locationAttributes;
}
