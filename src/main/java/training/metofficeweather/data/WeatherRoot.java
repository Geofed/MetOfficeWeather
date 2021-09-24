package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherRoot {
	@JsonProperty("SiteRep")
	public WeatherSiteRep weatherSiteRep;
}
