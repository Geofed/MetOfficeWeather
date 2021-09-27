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
		return  ":  Temperature: " + temp + "°C  ( feels like " + feelsLike + "°C )  " +
				"Humidity: " + humidity + "%  " +
				"Wind: " + windSpeed + " mph " + windDirection;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public String getFeelsLike() {
		return feelsLike;
	}

	public String getGustSpeed() {
		return gustSpeed;
	}

	public String getHumidity() {
		return humidity;
	}

	public String getPrecipitationPercent() {
		return precipitationPercent;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public String getTemp() {
		return temp;
	}

	public String getVisibility() {
		return visibility;
	}

	public String getWeatherCode() {
		return weatherCode;
	}

	public String getSolarUvIndex() {
		return solarUvIndex;
	}

	public String getMinAfterMidnight() {
		return minAfterMidnight;
	}
}
