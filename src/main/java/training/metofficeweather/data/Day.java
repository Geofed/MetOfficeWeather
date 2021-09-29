package training.metofficeweather.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;

public class Day {
	private DayofTheWeek dayOfTheWeek;
	private Date date;
	public WeatherDataAttributeRep data;
	private String stringDate;
	private String stringTime;

	public Day(DayofTheWeek dayOfTheWeek, Date date, WeatherDataAttributeRep data) {
		this.dayOfTheWeek = dayOfTheWeek;
		this.date = date;
		this.data = data;
		SimpleDateFormat format = new SimpleDateFormat("EEE MMM d");
		SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
		this.stringDate = format.format(date);
		this.stringTime = timeFormat.format(date);
	}

	public String getStringTime() {
		return stringTime;
	}

	public DayofTheWeek getDayOfTheWeek() {
		return dayOfTheWeek;
	}

	public void setDayOfTheWeek(DayofTheWeek dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public WeatherDataAttributeRep getData() {
		return data;
	}

	public void setData(WeatherDataAttributeRep data) {
		this.data = data;
	}

	public String getStringDate() {
		return stringDate;
	}

	public String getIcon() {
		String weatherCode = data.weatherCode;
		switch (weatherCode) {
			case ("0"):
				return "<i class=\"bi bi-app\"></i>";
			case ("1"):
				return "<i class=\"bi bi-sun\"></i>";
			case ("2"):
			case ("3"):
			case ("7"):
				return "<i class=\"bi bi-cloudy\"></i>";
			case ("5"):
			case ("6"):
				return "<i class=\"bi bi-water\"></i>";
			case ("8"):
				return "<i class=\"bi bi-cloud-sun\"></i>";
			case ("9"):
			case ("10"):
			case ("11"):
			case ("12"):
				return "<i class=\"bi bi-cloud-drizzle\"></i>";
			case ("13"):
			case ("14"):
			case ("15"):
				return "<i class=\"bi bi-cloud-rain-heavy\"></i>";
			case ("16"):
			case ("17"):
			case ("18"):
			case ("19"):
			case ("20"):
			case ("21"):
				return "<i class=\"bi bi-cloud-hail\"></i>";
			case ("22"):
			case ("23"):
			case ("24"):
			case ("25"):
			case ("26"):
			case ("27"):
				return "<i class=\"bi bi-cloud-snow\"></i>";
			case ("28"):
			case ("29"):
			case ("30"):
				return "<i class=\"bi bi-cloud-lightning\"></i>";

		}
		return null;
	}
}
