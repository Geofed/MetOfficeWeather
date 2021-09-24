package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataAtrributePeriod {
	public String type;
	public String value;
	@JsonProperty("Rep")
	public List<WeatherDataAttributeRep> rep;

	@Override
	public String toString() {

		Integer highestMinutes = 0;
		WeatherDataAttributeRep currentRep = rep.stream().findFirst().get();

		for (WeatherDataAttributeRep r : rep) {
			highestMinutes = (Integer.parseInt(r.minAfterMidnight) > highestMinutes) ? Integer.parseInt(r.minAfterMidnight) : highestMinutes;
			currentRep = r;
		}

		return currentRep.toString();
	}
}
