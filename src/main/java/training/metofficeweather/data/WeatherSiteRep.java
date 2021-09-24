package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherSiteRep {
	@JsonProperty("Wx")
	public WeatherDataAttributes dataAttributes;

	@JsonProperty("DV")
	public WeatherLocationAttriubutes locationAttributes;
}
