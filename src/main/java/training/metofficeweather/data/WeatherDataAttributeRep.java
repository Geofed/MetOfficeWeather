package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataAttributeRep {
	@JsonProperty("D")
	public String windDirection;
	@JsonProperty("F")
	public String feelsLike;
	@JsonProperty("G")
	public String gustSpeed;
	@JsonProperty("H")
	public String humidity;
	@JsonProperty("Pp")
	public String precipitationPercent;
	@JsonProperty("S")
	public String windSpeed;
	@JsonProperty("T")
	public String temp;
	@JsonProperty("V")
	public String visibility;
	@JsonProperty("W")
	public String weatherCode;
	@JsonProperty("U")
	public String solarUvIndex;
	@JsonProperty("$")
	public String minAfterMidnight;

	@Override
	public String toString() {
		Integer min = Integer.parseInt(minAfterMidnight);
		return  "\nTemperature: " + temp + "°C  ( feels like " + feelsLike + "°C )\n" +
				"Humidity: " + humidity + "%\n" +
				"Wind: " + windSpeed + " mph " + windDirection + " \n" +
				"Time: " + min / 60 + ":00";
	}
}
