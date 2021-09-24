package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherRoot {
	@JsonProperty("SiteRep")
	public WeatherSiteRep weatherSiteRep;

	@Override
	public String toString() {
		return weatherSiteRep.toString();
	}
}
