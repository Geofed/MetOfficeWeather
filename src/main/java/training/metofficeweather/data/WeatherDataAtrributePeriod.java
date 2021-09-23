package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherDataAtrributePeriod {
	public String type;
	public String value;
	@JsonProperty("Rep")
	public List<WeatherDataAttributeRep> rep;
}
