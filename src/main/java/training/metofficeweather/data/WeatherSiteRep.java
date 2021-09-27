package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherSiteRep {
	@JsonProperty("Wx")
	public WeatherDataAttributes dataAttributes;

	@JsonProperty("DV")
	public WeatherLocationAttributes locationAttributes;

	@Override
	public String toString() {
		return  locationAttributes.toString();
	}



}
