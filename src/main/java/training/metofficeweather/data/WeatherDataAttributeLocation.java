package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataAttributeLocation {
	public String i;
	public String lat;
	public String lon;
	public String name;
	public String country;
	public String continent;
	public String elevation;
	@JsonProperty("Period")
	public List<WeatherDataAtrributePeriod> period;

	@Override
	public String toString() {

		WeatherDataAtrributePeriod mostRecentDay = period.stream().findFirst().get();

		for (WeatherDataAtrributePeriod w : period) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = format.format( new Date());
				if (format.parse(mostRecentDay.value.substring(0, 10)).before(format.parse(w.value.substring(0, 10))));
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return mostRecentDay.value.substring(0, 10) + mostRecentDay.toString();
	}
}
