package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class WeatherDataAttributeParam {
	@JsonProperty("Param")
	ArrayList<WeatherDataAttributes> param;
}
